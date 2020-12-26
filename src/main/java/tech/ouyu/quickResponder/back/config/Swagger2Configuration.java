package tech.ouyu.quickResponder.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
   //api接口包扫描路径
   public static final String SWAGGER_SCAN_BASE_PACKAGE = "tech.ouyu.quickResponder.back";

   public static final String VERSION = "1.0.0";

   @Bean
   public Docket createRestApi() {
       List<Parameter> aParameters = getParameters();

       return new Docket(DocumentationType.SWAGGER_2)
               .apiInfo(apiInfo())
               .select()
               .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
               .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
               .build()
               .globalOperationParameters(aParameters);
   }

   /**
    * 添加请求参数
    * @author ouyu
    */
    private List<Parameter> getParameters() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(DescribeConstant.Header.X_CURRENT_TOKEN)
                .description("token令牌").
                 modelRef(new ModelRef("String"))
                .parameterType("header").required(true).build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(tokenPar.build());
        return aParameters;
    }

    private ApiInfo apiInfo() {
       return new ApiInfoBuilder()
                   .title("快速抢答服务") //设置文档的标题
                   .description("快速抢答服务 API 接口文档") // 设置文档的描述
                   .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                   .termsOfServiceUrl("http://www.baidu.com") // 设置文档的License信息->1.3 License information
                   .build();
   }
}