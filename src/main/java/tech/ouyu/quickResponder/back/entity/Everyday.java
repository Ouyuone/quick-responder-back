package tech.ouyu.quickResponder.back.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Fri Dec 11 11:38:33 CST 2020.</p>
 *
 * @author (ousakai)
 */

@ApiModel("每日一句")
@TableName( value ="everyday" )
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Everyday{



   	@TableId(value = "id",type = IdType.AUTO)
	private Long id;

	/**
	 * 每日一句
	 */
	@ApiModelProperty(value = "每日一句话")
	@TableField(value = "oneword" )
	private String oneword;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
   	@TableField(value = "author" )
	private String author;

	@TableField(select = false)
	@TableLogic(value = "0",delval = "1")
	private Boolean isDel;

	@TableField(fill = FieldFill.INSERT)
	private Long createBy;


	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
