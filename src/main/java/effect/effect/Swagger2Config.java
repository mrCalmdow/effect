package effect.effect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Beldon.
 * @create 2017-11-3 下午2:26
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.token:}")
    private String defaultToken;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("x-user-token")
                .description("鉴权令牌")
                .modelRef(new ModelRef("string"))
                .defaultValue(defaultToken)
                .parameterType("header")
                .required(false);
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)
                .select()
                .apis(RequestHandlerSelectors.basePackage("effect.effect"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("博客后台接口文档")
                .description("用于实时更新博客后台接口信息")
                .termsOfServiceUrl("http://www.effect.com/")
                .contact(new Contact("一个博客","http://www.effect.com/","xx@xx.com"))
                .version("1.0")
                .build();
    }

}
