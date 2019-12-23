package com.mloine.msclass.service;

import com.mloine.msclass.domain.dto.UserDto;
import com.mloine.msclass.domain.dto.UserMoneyDto;
import com.mloine.msclass.domain.entity.Lesson;
import com.mloine.msclass.domain.entity.LessonUser;
import com.mloine.msclass.feign.MsUserFeignClient;
import com.mloine.msclass.repository.LessionResipotory;
import com.mloine.msclass.repository.LessionUserResipotory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Transactional(rollbackFor = Exception.class)
@Service
public class LessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessionService.class);

    @Autowired
    private LessionResipotory lessionResipotory;

    @Autowired
    private LessionUserResipotory lessionUserResipotory;

    @Autowired
    private RestTemplate restTemplate;

    //consul discoery 工具
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private MsUserFeignClient msUserFeignClient;

    @Autowired
    private Source source;

    public Lesson buyById(Integer id, HttpServletRequest request) throws IllegalAccessException {

        //1.根据id查询lession
        Lesson lesson = this.lessionResipotory.findById(id).orElseThrow(() -> new IllegalArgumentException("该课程不存在！"));
        //2.根据lession ，id查询user_lession, 如果没有
        LessonUser byLessionId = this.lessionUserResipotory.findByLessonId(id);
        if(byLessionId != null){
            return lesson;
        }
        //3.如果user_lession == null && 用户余额大于 lession。price
        // TODO 登录实现后需要重构
//        Integer userId = 1;
        Integer userId= (Integer) request.getAttribute("userId");
//        List<ServiceInstance> instances = this.discoveryClient.getInstances("ms-user");
//
//
//        //
//        //南京机房的用户微服务实例
////        List<ServiceInstance> userInstancesInNJJIFANG = instances.stream().filter(instance -> {
////            String jifang = instance.getMetadata().get("JIFANG");
////            if ("NJ".equals(jifang)) {
////                return true;
////            }
////            return false;
////        }).collect(Collectors.toList());
//
//        //简陋负载均衡
//        int i = ThreadLocalRandom.current().nextInt(instances.size());
//        URI uri = instances.get(i).getUri();
//
//        LOGGER.info("选择的用户微服务实例的地址是 = {}",uri);

        /**
         * 1.代码可读性差
         * 2.复杂url构造困难
         * 3.编程风格不统一 IDE无法提示
         */
//        UserDto userDto = restTemplate.getForObject(
//                "http://ms-user/users/{id}",
//                UserDto.class,
//                userId
//        );

        UserDto userDto = this.msUserFeignClient.findUserById(userId);

        BigDecimal money = userDto.getMoney().subtract(lesson.getPrice());
        if(money.doubleValue() >= 0){

        }else{
            throw new IllegalAccessException("余额不足");
        }

        // TODO 购买逻辑 1.调用用户微服务的扣减金额接口
        boolean flag = this.source.output()
                .send(
                        MessageBuilder
                                .withPayload(
                                        new UserMoneyDto(
                                                userId,
                                                lesson.getPrice(),
                                                "购买课程",
                                                String.format("%s购买了id为%s的课程", userId, id)
                                        )
                ).build());

        // TODO 2.向lession_user表插入数据
        LessonUser lessonUser = new LessonUser();
        lessonUser.setUserId(userId);
        lessonUser.setLessonId(id);
        this.lessionUserResipotory.save(lessonUser);
        return lesson;
    }

}
