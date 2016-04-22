[#ftl strip_whitespace=true]
[#macro application]
[#include "/include/_navigation.html" /]
<div id="main-content" class="wrapper">
  [#-- Main --]
  [#nested/]
</div>
[#include "/include/_dialog.html" /]
[#include "/include/_sideslip.html" /]
[#include "/include/_template.html" /]
[/#macro]
