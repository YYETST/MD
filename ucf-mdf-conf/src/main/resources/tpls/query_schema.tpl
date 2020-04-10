{
	"fields":[
<#*(attribute:attributes)?((isCompositionAttribute!=true || isRoleA!=true) && isDerived!=true && isDependencied!=true){#>
		{"name":"<#=name#>"},
<#?(isAssociationAttribute==true){#>
		<#*(attribute:type.attributes)?((isCompositionAttribute!=true || isRoleA!=true) && isDerived!=true  && isDependencied!=true && isKey!=true){#>
		{"name":"<#=_super.name#>.<#=name#>"},
<#?(isAssociationAttribute==true){#>
		<#*(attribute:type.attributes)?((isCompositionAttribute!=true || isRoleA!=true) && isDerived!=true  && isDependencied!=true && isKey!=true){#>
		{"name":"<#=_super._super.name#>.<#=_super.name#>.<#=name#>"},
		<#}#>
<#}#>
		<#}#>
<#}#>
<#}#>
<#?(syncAttribute!=null){#>
		{"name":"<#=syncAttribute.name#>"}
<#}#>
<#?(syncAttribute==null){#>
		{"name":"1","alias":"_placeholder"}
<#}#>
	],
	"conditions":[
		{"name":"id","v1":<#/key#>,"op":"eq"}
	],
	"orders":[
		{"name":"id","order":"desc"}
	],
	"pager":{"pageIndex":1,"pageSize":10}
}
