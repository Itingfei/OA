<div class="accordion" id="menu-${menuParentId}">
	
	<#assign firstMenu = true>
	<#list menuList as menu>
	
	<#if menuParentId ??>
	<#else>
	<#assign menuParentId = 1>
	</#if>
	<#if menu.parentId == menuParentId >
		
		
			<div class="accordion-group">
		    <div class="accordion-heading"> 
		    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu-${menuParentId}" data-href="#collapse-${menu.id}" href="#collapse-${menu.id}" ><i class="icon-chevron-<#if firstMenu ?? && firstMenu>down<#else>right</#if>"></i>&nbsp;${menu.name}</a>
		    </div>
		    <div id="collapse-${menu.id}" class="accordion-body collapse <#if firstMenu ?? && firstMenu>in<#else></#if>">
				<div class="accordion-inner">
					<ul class="nav nav-list">
					<#list menuList as menu2>
					<#if menu2.parentId == menu.id>
					
						<li><a data-href=".menu3-${menu2.id}" href="${request.contextPath}/<#if menu2.href ??>${menu2.href}<#else>/404</#if>" target="mainFrame" ><i class="icon-<#if menu2.icon ?? && menu2.icon != "">${menu2.icon}<#else>circle-arrow-right</#if>"></i>&nbsp;${menu2.name}</a>
							<ul class="nav nav-list hide" style="margin:0;padding-right:0;">
							<#list menuList as menu3>
							<#if menu3.parentId == menu2.id>
								<li class="menu3-${menu2.id} hide"><a href="${request.contextPath}/<#if menu3.href ??>${menu3.href}<#else>/404</#if>" target="mainFrame" ><%--<i class="icon-<#if menu3.icon ??>${menu3.icon}<#else>circle-arrow-right</#if>"></i>--%>&nbsp;${menu3.name}</a></li>
							</#if>
							</#list></ul></li>
							<#----<c:set var="firstMenu" value="false"/>--->
							<#assign firstMenu = false>
					</#if>
					</#list>
							</ul>
				</div>
		    </div>
		</div>
	</#if>
	</#list>
</div>