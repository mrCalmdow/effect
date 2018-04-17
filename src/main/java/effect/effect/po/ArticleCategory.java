package effect.effect.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:15
 */
@Entity
@Table(name = "t_article_category")
@Data
public class ArticleCategory {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序，越小越靠后
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "cre_time")
    private Date creTime;

    /**
     * 归属用户
     */
    @Column(length = 32)
    private String belongTo;

}
