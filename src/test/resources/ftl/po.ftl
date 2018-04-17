package effect.effect.po;

<#list importType as im>
import ${im!};
</#list>
import lombok.Data;
@Data


public class ${poName!} {

<#list columnDatas as columnData>

    <#if !columnData.column.columnComment??>
   /**
    * ${(columnData.column.columnComment)!}
    */
    </#if>
    private ${(columnData.type.javaType)!} ${(columnData.name)!};
</#list>

}