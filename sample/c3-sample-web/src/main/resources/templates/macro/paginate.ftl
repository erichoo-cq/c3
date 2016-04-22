[#ftl strip_whitespace=true]
[#macro paginate page]
[#if page??]
	<nav>
	  <ul class="pagination m-no">
	  	[#if page.first]
	  		<li class="disabled"><span aria-hidden="true"><i class="fa fa-chevron-left"></i></span></li>
	  	[#else]
	  		<li><a href="?page=[#if (page.number - 1) < 0]0[#else]${page.number-1}[/#if]" data-page="[#if (page.number - 1) < 0]0[#else]${page.number-1}[/#if]" aria-label="上一页"><span aria-hidden="true"><i class="fa fa-chevron-left"></i></span></a></li>
	  	[/#if]
	  	[#if page.totalPages == 0]
	  		<li class="active"><a>1 <span class="sr-only">(当前页)</span></a></li>
	  	[#elseif page.totalPages lte 5 && page.totalPages gt 0]	
	  		[#list 1..page.totalPages as idx]
	  			[#if (idx-1) == page.number]
	  				<li class="active"><a data-page="${idx-1}">${idx} <span class="sr-only">(当前页)</span></a></li>
	  			[#else]
	  				<li><a href="?page=${idx-1}" data-page="${idx-1}">${idx}</a></li>
	  			[/#if]
	  		[/#list]
	  	[#else]
	  		[#if (page.number+3) gte page.totalPages]
	  			[#list (page.totalPages-5)..page.totalPages as idx]
		  			[#if (idx-1) == page.number]
		  				<li class="active"><a data-page="${idx-1}">${idx} <span class="sr-only">(当前页)</span></a></li>
		  			[#else]
		  				<li><a href="?page=${idx-1}" data-page="${idx-1}">${idx}</a></li>
		  			[/#if]
		  		[/#list]
		  	[#elseif (page.number-3) lte 0]	
		  		[#list 1..5 as idx]
		  			[#if (idx-1) == page.number]
		  				<li class="active"><a data-page="${idx-1}">${idx} <span class="sr-only">(当前页)</span></a></li>
		  			[#else]
		  				<li><a href="?page=${idx-1}" data-page="${idx-1}">${idx}</a></li>
		  			[/#if]
		  		[/#list]
		  	[#else]
		  		[#list (page.number-2)..(page.number+3) as idx]
		  			[#if (idx-1) == page.number]
		  				<li class="active"><a data-page="${idx-1}">${idx} <span class="sr-only">(当前页)</span></a></li>
		  			[#else]
		  				<li><a href="?page=${idx-1}" data-page="${idx-1}">${idx}</a></li>
		  			[/#if]
		  		[/#list]
	  		[/#if]
	  	[/#if]
	    [#if page.last]
	    	<li class="disabled"><span aria-hidden="true"><i class="fa fa-chevron-right"></i></span></li>
	    [#else]
	    	<li><a href="?page=[#if (page.number + 1) > page.totalPages]${page.totalPages}[#else]${page.number + 1}[/#if]" data-page="[#if (page.number + 1) > page.totalPages]${page.totalPages}[#else]${page.number + 1}[/#if]" aria-label="下一页"><span aria-hidden="true"><i class="fa fa-chevron-right"></i></span></a></li>
	    [/#if]
	  </ul>
	</nav>
[/#if]	
[/#macro]

[#macro paginate_entries page]
[#if page??]
当前显示 ${page.number*page.size+1}-${page.number*page.size + page.numberOfElements}，共 ${page.totalElements} 条记录
[#else]
当前显示 0-0，共 0 条记录
[/#if]
[/#macro]