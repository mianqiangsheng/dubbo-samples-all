/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.action.GreetingServiceConsumer;
import org.springframework.context.annotation.*;

public class ConsumerBootstrap {

    public static void main(String[] args) {

//        System.setProperty("dubbo.application.qos.enable", "true");
//        System.setProperty("dubbo.application.qos.port", "33333");
//        System.setProperty("dubbo.application.qos.accept.foreign.ip", "false");

//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setQosPort(33333);
//        applicationConfig.setQosAcceptForeignIp(false);
//        applicationConfig.setQosEnable(true);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        GreetingServiceConsumer greetingServiceConsumer = context.getBean(GreetingServiceConsumer.class);
        String hello = greetingServiceConsumer.doSayHello("nacos");
        System.out.println("result: " + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.action"})
    static class ConsumerConfiguration {

        /**
         * 参考https://blog.csdn.net/u013202238/article/details/81432784
         * 这里配置有效！
         */
        @Bean
        public ApplicationConfig applicationConfig(){
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setQosPort(33333);
            applicationConfig.setQosAcceptForeignIp(false);
            applicationConfig.setQosEnable(true);

            return applicationConfig;
        }
    }
}
