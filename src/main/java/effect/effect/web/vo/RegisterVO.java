package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @outhor feilongchen
 * @create 2018-01-22 6:53 PM
 */
@Getter
@Setter
public class RegisterVO extends BaseVO {

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "username", required = true)
    @NotBlank(message = "username can't be empty")
    @Length(min = 6, max = 12, message = "username length must between 6 and 12")
    private String account;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "nickname", required = true)
    @NotBlank(message = "nickname can not be empty")
    @Length(min = 3, max = 12, message = "nickname length must between 3 and 12")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "password", required = true)
    @NotBlank(message = "password can not be empty")
    @Length(min = 6, max = 12, message = "password length must between 3 and 12")
    private String password;

    /**
     * email
     */
    @ApiModelProperty(value = "email", required = true)
    @NotBlank(message = "email can not be empty")
    @Pattern(regexp = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", message = "email format is not correct")
    private String email;

}
