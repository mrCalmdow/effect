package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-01-29 10:28 AM
 */
@Getter
@Setter
public class ResetPasswordVO extends BaseVO {

    @ApiModelProperty(value = "username", required = true)
    @NotBlank
    private String account;

    @ApiModelProperty(value = "oldPassword", required = true)
    @NotBlank
    @Length(min = 6, max = 12, message = "password length must between 3 and 12")
    private String oldPassword;

    @ApiModelProperty(value = "newPassword", required = true)
    @NotBlank
    @Length(min = 6, max = 12, message = "password length must between 3 and 12")
    private String newPassword;
}
