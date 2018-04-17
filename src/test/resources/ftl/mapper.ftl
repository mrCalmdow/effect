package effect.effect.mapper;

import effect.effect.po.${poName!};
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ${(table.tableComment)!}
 * Created by ${(data.author)!}.
 */
@Repository
public interface ${poName!}Mapper {

    int insert(${poName!} ${poName?uncap_first!});

    int insertSelective(${poName!} ${poName?uncap_first!});

    @Delete("delete from ${tableName!} where ${(primaryColumnData.column.columnName)!} = ${r"#{"}${(primaryColumnData.name)!}${r"}"}")
    int deleteByPrimaryKey(${(primaryColumnData.type.javaType)!} ${(primaryColumnData.name)!});


}