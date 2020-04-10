package meta;

import java.util.HashMap;

public class Meta {
	private static HashMap<String, Relation> metamap;
	static {
		metamap = new HashMap<String, Relation>();
	}
	static {
<#*(component:components){#>
	<#?(associations!=null) && (associations._size>0){#>
	<#*(association:associations){#>
		<#?(type==composition)#>
		metamap.put("<#=typeA.name#>.<#=roleB.name#>", new Relation(RelationType.Association, "<#=typeB.name#>", "<#=typeB.keyAttribute.name#>"));
		metamap.put("<#=typeB.name#>.<#=roleA.name#>", new Relation(RelationType.Composition, "<#=typeA.name#>", "<#=roleB.name#>"));
		<#}#>
	<#}#>
	<#}#>
	<#?(classes!=null) && (classes._size>0){#>
	<#*(class:classes){#>
		<#?(attributes!=null) && (attributes._size>0){#>
		<#*(attr:attributes){#>
			<#?(isAssociationAttribute==true && (isCompositionAttribute!=true || isRoleB==true)){#>
		metamap.put("<#=owner.name#>.<#=name#>", new Relation(RelationType.Association, "<#=type.name#>", "<#=type.keyAttribute.name#>"));
			<#}#>
		<#}#>
		<#}#>
	<#}#>
	<#}#>
<#}#>
	}

	public static Relation relation(String path) {
		return metamap.get(path);
	}
}
