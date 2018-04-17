package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-01-23 11:04 PM
 */
@Getter
@Setter
public class LoginVO extends BaseVO {

    @ApiModelProperty(value = "username", required = true)
    @NotBlank(message = "username can't be empty")
    private String account;

    @ApiModelProperty(value = "password", required = true)
    @NotBlank(message = "password can not be empty")
    private String password;

}
