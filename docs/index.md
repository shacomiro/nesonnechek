<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="generator" content="Asciidoctor 2.0.10">
<title>Nesonnechek API Document</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic%7CNoto+Serif:400,400italic,700,700italic%7CDroid+Sans+Mono:400,700">
<style>
/* Asciidoctor default stylesheet | MIT License | https://asciidoctor.org */
/* Uncomment @import statement to use as custom stylesheet */
/*@import "https://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic%7CNoto+Serif:400,400italic,700,700italic%7CDroid+Sans+Mono:400,700";*/
article,aside,details,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}
audio,video{display:inline-block}
audio:not([controls]){display:none;height:0}
html{font-family:sans-serif;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}
a{background:none}
a:focus{outline:thin dotted}
a:active,a:hover{outline:0}
h1{font-size:2em;margin:.67em 0}
abbr[title]{border-bottom:1px dotted}
b,strong{font-weight:bold}
dfn{font-style:italic}
hr{-moz-box-sizing:content-box;box-sizing:content-box;height:0}
mark{background:#ff0;color:#000}
code,kbd,pre,samp{font-family:monospace;font-size:1em}
pre{white-space:pre-wrap}
q{quotes:"\201C" "\201D" "\2018" "\2019"}
small{font-size:80%}
sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}
sup{top:-.5em}
sub{bottom:-.25em}
img{border:0}
svg:not(:root){overflow:hidden}
figure{margin:0}
fieldset{border:1px solid silver;margin:0 2px;padding:.35em .625em .75em}
legend{border:0;padding:0}
button,input,select,textarea{font-family:inherit;font-size:100%;margin:0}
button,input{line-height:normal}
button,select{text-transform:none}
button,html input[type="button"],input[type="reset"],input[type="submit"]{-webkit-appearance:button;cursor:pointer}
button[disabled],html input[disabled]{cursor:default}
input[type="checkbox"],input[type="radio"]{box-sizing:border-box;padding:0}
button::-moz-focus-inner,input::-moz-focus-inner{border:0;padding:0}
textarea{overflow:auto;vertical-align:top}
table{border-collapse:collapse;border-spacing:0}
*,*::before,*::after{-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box}
html,body{font-size:100%}
body{background:#fff;color:rgba(0,0,0,.8);padding:0;margin:0;font-family:"Noto Serif","DejaVu Serif",serif;font-weight:400;font-style:normal;line-height:1;position:relative;cursor:auto;tab-size:4;-moz-osx-font-smoothing:grayscale;-webkit-font-smoothing:antialiased}
a:hover{cursor:pointer}
img,object,embed{max-width:100%;height:auto}
object,embed{height:100%}
img{-ms-interpolation-mode:bicubic}
.left{float:left!important}
.right{float:right!important}
.text-left{text-align:left!important}
.text-right{text-align:right!important}
.text-center{text-align:center!important}
.text-justify{text-align:justify!important}
.hide{display:none}
img,object,svg{display:inline-block;vertical-align:middle}
textarea{height:auto;min-height:50px}
select{width:100%}
.center{margin-left:auto;margin-right:auto}
.stretch{width:100%}
.subheader,.admonitionblock td.content>.title,.audioblock>.title,.exampleblock>.title,.imageblock>.title,.listingblock>.title,.literalblock>.title,.stemblock>.title,.openblock>.title,.paragraph>.title,.quoteblock>.title,table.tableblock>.title,.verseblock>.title,.videoblock>.title,.dlist>.title,.olist>.title,.ulist>.title,.qlist>.title,.hdlist>.title{line-height:1.45;color:#7a2518;font-weight:400;margin-top:0;margin-bottom:.25em}
div,dl,dt,dd,ul,ol,li,h1,h2,h3,#toctitle,.sidebarblock>.content>.title,h4,h5,h6,pre,form,p,blockquote,th,td{margin:0;padding:0;direction:ltr}
a{color:#2156a5;text-decoration:underline;line-height:inherit}
a:hover,a:focus{color:#1d4b8f}
a img{border:0}
p{font-family:inherit;font-weight:400;font-size:1em;line-height:1.6;margin-bottom:1.25em;text-rendering:optimizeLegibility}
p aside{font-size:.875em;line-height:1.35;font-style:italic}
h1,h2,h3,#toctitle,.sidebarblock>.content>.title,h4,h5,h6{font-family:"Open Sans","DejaVu Sans",sans-serif;font-weight:300;font-style:normal;color:#ba3925;text-rendering:optimizeLegibility;margin-top:1em;margin-bottom:.5em;line-height:1.0125em}
h1 small,h2 small,h3 small,#toctitle small,.sidebarblock>.content>.title small,h4 small,h5 small,h6 small{font-size:60%;color:#e99b8f;line-height:0}
h1{font-size:2.125em}
h2{font-size:1.6875em}
h3,#toctitle,.sidebarblock>.content>.title{font-size:1.375em}
h4,h5{font-size:1.125em}
h6{font-size:1em}
hr{border:solid #dddddf;border-width:1px 0 0;clear:both;margin:1.25em 0 1.1875em;height:0}
em,i{font-style:italic;line-height:inherit}
strong,b{font-weight:bold;line-height:inherit}
small{font-size:60%;line-height:inherit}
code{font-family:"Droid Sans Mono","DejaVu Sans Mono",monospace;font-weight:400;color:rgba(0,0,0,.9)}
ul,ol,dl{font-size:1em;line-height:1.6;margin-bottom:1.25em;list-style-position:outside;font-family:inherit}
ul,ol{margin-left:1.5em}
ul li ul,ul li ol{margin-left:1.25em;margin-bottom:0;font-size:1em}
ul.square li ul,ul.circle li ul,ul.disc li ul{list-style:inherit}
ul.square{list-style-type:square}
ul.circle{list-style-type:circle}
ul.disc{list-style-type:disc}
ol li ul,ol li ol{margin-left:1.25em;margin-bottom:0}
dl dt{margin-bottom:.3125em;font-weight:bold}
dl dd{margin-bottom:1.25em}
abbr,acronym{text-transform:uppercase;font-size:90%;color:rgba(0,0,0,.8);border-bottom:1px dotted #ddd;cursor:help}
abbr{text-transform:none}
blockquote{margin:0 0 1.25em;padding:.5625em 1.25em 0 1.1875em;border-left:1px solid #ddd}
blockquote cite{display:block;font-size:.9375em;color:rgba(0,0,0,.6)}
blockquote cite::before{content:"\2014 \0020"}
blockquote cite a,blockquote cite a:visited{color:rgba(0,0,0,.6)}
blockquote,blockquote p{line-height:1.6;color:rgba(0,0,0,.85)}
@media screen and (min-width:768px){h1,h2,h3,#toctitle,.sidebarblock>.content>.title,h4,h5,h6{line-height:1.2}
h1{font-size:2.75em}
h2{font-size:2.3125em}
h3,#toctitle,.sidebarblock>.content>.title{font-size:1.6875em}
h4{font-size:1.4375em}}
table{background:#fff;margin-bottom:1.25em;border:solid 1px #dedede}
table thead,table tfoot{background:#f7f8f7}
table thead tr th,table thead tr td,table tfoot tr th,table tfoot tr td{padding:.5em .625em .625em;font-size:inherit;color:rgba(0,0,0,.8);text-align:left}
table tr th,table tr td{padding:.5625em .625em;font-size:inherit;color:rgba(0,0,0,.8)}
table tr.even,table tr.alt{background:#f8f8f7}
table thead tr th,table tfoot tr th,table tbody tr td,table tr td,table tfoot tr td{display:table-cell;line-height:1.6}
h1,h2,h3,#toctitle,.sidebarblock>.content>.title,h4,h5,h6{line-height:1.2;word-spacing:-.05em}
h1 strong,h2 strong,h3 strong,#toctitle strong,.sidebarblock>.content>.title strong,h4 strong,h5 strong,h6 strong{font-weight:400}
.clearfix::before,.clearfix::after,.float-group::before,.float-group::after{content:" ";display:table}
.clearfix::after,.float-group::after{clear:both}
:not(pre):not([class^=L])>code{font-size:.9375em;font-style:normal!important;letter-spacing:0;padding:.1em .5ex;word-spacing:-.15em;background:#f7f7f8;-webkit-border-radius:4px;border-radius:4px;line-height:1.45;text-rendering:optimizeSpeed;word-wrap:break-word}
:not(pre)>code.nobreak{word-wrap:normal}
:not(pre)>code.nowrap{white-space:nowrap}
pre{color:rgba(0,0,0,.9);font-family:"Droid Sans Mono","DejaVu Sans Mono",monospace;line-height:1.45;text-rendering:optimizeSpeed}
pre code,pre pre{color:inherit;font-size:inherit;line-height:inherit}
pre>code{display:block}
pre.nowrap,pre.nowrap pre{white-space:pre;word-wrap:normal}
em em{font-style:normal}
strong strong{font-weight:400}
.keyseq{color:rgba(51,51,51,.8)}
kbd{font-family:"Droid Sans Mono","DejaVu Sans Mono",monospace;display:inline-block;color:rgba(0,0,0,.8);font-size:.65em;line-height:1.45;background:#f7f7f7;border:1px solid #ccc;-webkit-border-radius:3px;border-radius:3px;-webkit-box-shadow:0 1px 0 rgba(0,0,0,.2),0 0 0 .1em white inset;box-shadow:0 1px 0 rgba(0,0,0,.2),0 0 0 .1em #fff inset;margin:0 .15em;padding:.2em .5em;vertical-align:middle;position:relative;top:-.1em;white-space:nowrap}
.keyseq kbd:first-child{margin-left:0}
.keyseq kbd:last-child{margin-right:0}
.menuseq,.menuref{color:#000}
.menuseq b:not(.caret),.menuref{font-weight:inherit}
.menuseq{word-spacing:-.02em}
.menuseq b.caret{font-size:1.25em;line-height:.8}
.menuseq i.caret{font-weight:bold;text-align:center;width:.45em}
b.button::before,b.button::after{position:relative;top:-1px;font-weight:400}
b.button::before{content:"[";padding:0 3px 0 2px}
b.button::after{content:"]";padding:0 2px 0 3px}
p a>code:hover{color:rgba(0,0,0,.9)}
#header,#content,#footnotes,#footer{width:100%;margin-left:auto;margin-right:auto;margin-top:0;margin-bottom:0;max-width:62.5em;*zoom:1;position:relative;padding-left:.9375em;padding-right:.9375em}
#header::before,#header::after,#content::before,#content::after,#footnotes::before,#footnotes::after,#footer::before,#footer::after{content:" ";display:table}
#header::after,#content::after,#footnotes::after,#footer::after{clear:both}
#content{margin-top:1.25em}
#content::before{content:none}
#header>h1:first-child{color:rgba(0,0,0,.85);margin-top:2.25rem;margin-bottom:0}
#header>h1:first-child+#toc{margin-top:8px;border-top:1px solid #dddddf}
#header>h1:only-child,body.toc2 #header>h1:nth-last-child(2){border-bottom:1px solid #dddddf;padding-bottom:8px}
#header .details{border-bottom:1px solid #dddddf;line-height:1.45;padding-top:.25em;padding-bottom:.25em;padding-left:.25em;color:rgba(0,0,0,.6);display:-ms-flexbox;display:-webkit-flex;display:flex;-ms-flex-flow:row wrap;-webkit-flex-flow:row wrap;flex-flow:row wrap}
#header .details span:first-child{margin-left:-.125em}
#header .details span.email a{color:rgba(0,0,0,.85)}
#header .details br{display:none}
#header .details br+span::before{content:"\00a0\2013\00a0"}
#header .details br+span.author::before{content:"\00a0\22c5\00a0";color:rgba(0,0,0,.85)}
#header .details br+span#revremark::before{content:"\00a0|\00a0"}
#header #revnumber{text-transform:capitalize}
#header #revnumber::after{content:"\00a0"}
#content>h1:first-child:not([class]){color:rgba(0,0,0,.85);border-bottom:1px solid #dddddf;padding-bottom:8px;margin-top:0;padding-top:1rem;margin-bottom:1.25rem}
#toc{border-bottom:1px solid #e7e7e9;padding-bottom:.5em}
#toc>ul{margin-left:.125em}
#toc ul.sectlevel0>li>a{font-style:italic}
#toc ul.sectlevel0 ul.sectlevel1{margin:.5em 0}
#toc ul{font-family:"Open Sans","DejaVu Sans",sans-serif;list-style-type:none}
#toc li{line-height:1.3334;margin-top:.3334em}
#toc a{text-decoration:none}
#toc a:active{text-decoration:underline}
#toctitle{color:#7a2518;font-size:1.2em}
@media screen and (min-width:768px){#toctitle{font-size:1.375em}
body.toc2{padding-left:15em;padding-right:0}
#toc.toc2{margin-top:0!important;background:#f8f8f7;position:fixed;width:15em;left:0;top:0;border-right:1px solid #e7e7e9;border-top-width:0!important;border-bottom-width:0!important;z-index:1000;padding:1.25em 1em;height:100%;overflow:auto}
#toc.toc2 #toctitle{margin-top:0;margin-bottom:.8rem;font-size:1.2em}
#toc.toc2>ul{font-size:.9em;margin-bottom:0}
#toc.toc2 ul ul{margin-left:0;padding-left:1em}
#toc.toc2 ul.sectlevel0 ul.sectlevel1{padding-left:0;margin-top:.5em;margin-bottom:.5em}
body.toc2.toc-right{padding-left:0;padding-right:15em}
body.toc2.toc-right #toc.toc2{border-right-width:0;border-left:1px solid #e7e7e9;left:auto;right:0}}
@media screen and (min-width:1280px){body.toc2{padding-left:20em;padding-right:0}
#toc.toc2{width:20em}
#toc.toc2 #toctitle{font-size:1.375em}
#toc.toc2>ul{font-size:.95em}
#toc.toc2 ul ul{padding-left:1.25em}
body.toc2.toc-right{padding-left:0;padding-right:20em}}
#content #toc{border-style:solid;border-width:1px;border-color:#e0e0dc;margin-bottom:1.25em;padding:1.25em;background:#f8f8f7;-webkit-border-radius:4px;border-radius:4px}
#content #toc>:first-child{margin-top:0}
#content #toc>:last-child{margin-bottom:0}
#footer{max-width:100%;background:rgba(0,0,0,.8);padding:1.25em}
#footer-text{color:rgba(255,255,255,.8);line-height:1.44}
#content{margin-bottom:.625em}
.sect1{padding-bottom:.625em}
@media screen and (min-width:768px){#content{margin-bottom:1.25em}
.sect1{padding-bottom:1.25em}}
.sect1:last-child{padding-bottom:0}
.sect1+.sect1{border-top:1px solid #e7e7e9}
#content h1>a.anchor,h2>a.anchor,h3>a.anchor,#toctitle>a.anchor,.sidebarblock>.content>.title>a.anchor,h4>a.anchor,h5>a.anchor,h6>a.anchor{position:absolute;z-index:1001;width:1.5ex;margin-left:-1.5ex;display:block;text-decoration:none!important;visibility:hidden;text-align:center;font-weight:400}
#content h1>a.anchor::before,h2>a.anchor::before,h3>a.anchor::before,#toctitle>a.anchor::before,.sidebarblock>.content>.title>a.anchor::before,h4>a.anchor::before,h5>a.anchor::before,h6>a.anchor::before{content:"\00A7";font-size:.85em;display:block;padding-top:.1em}
#content h1:hover>a.anchor,#content h1>a.anchor:hover,h2:hover>a.anchor,h2>a.anchor:hover,h3:hover>a.anchor,#toctitle:hover>a.anchor,.sidebarblock>.content>.title:hover>a.anchor,h3>a.anchor:hover,#toctitle>a.anchor:hover,.sidebarblock>.content>.title>a.anchor:hover,h4:hover>a.anchor,h4>a.anchor:hover,h5:hover>a.anchor,h5>a.anchor:hover,h6:hover>a.anchor,h6>a.anchor:hover{visibility:visible}
#content h1>a.link,h2>a.link,h3>a.link,#toctitle>a.link,.sidebarblock>.content>.title>a.link,h4>a.link,h5>a.link,h6>a.link{color:#ba3925;text-decoration:none}
#content h1>a.link:hover,h2>a.link:hover,h3>a.link:hover,#toctitle>a.link:hover,.sidebarblock>.content>.title>a.link:hover,h4>a.link:hover,h5>a.link:hover,h6>a.link:hover{color:#a53221}
details,.audioblock,.imageblock,.literalblock,.listingblock,.stemblock,.videoblock{margin-bottom:1.25em}
details>summary:first-of-type{cursor:pointer;display:list-item;outline:none;margin-bottom:.75em}
.admonitionblock td.content>.title,.audioblock>.title,.exampleblock>.title,.imageblock>.title,.listingblock>.title,.literalblock>.title,.stemblock>.title,.openblock>.title,.paragraph>.title,.quoteblock>.title,table.tableblock>.title,.verseblock>.title,.videoblock>.title,.dlist>.title,.olist>.title,.ulist>.title,.qlist>.title,.hdlist>.title{text-rendering:optimizeLegibility;text-align:left;font-family:"Noto Serif","DejaVu Serif",serif;font-size:1rem;font-style:italic}
table.tableblock.fit-content>caption.title{white-space:nowrap;width:0}
.paragraph.lead>p,#preamble>.sectionbody>[class="paragraph"]:first-of-type p{font-size:1.21875em;line-height:1.6;color:rgba(0,0,0,.85)}
table.tableblock #preamble>.sectionbody>[class="paragraph"]:first-of-type p{font-size:inherit}
.admonitionblock>table{border-collapse:separate;border:0;background:none;width:100%}
.admonitionblock>table td.icon{text-align:center;width:80px}
.admonitionblock>table td.icon img{max-width:none}
.admonitionblock>table td.icon .title{font-weight:bold;font-family:"Open Sans","DejaVu Sans",sans-serif;text-transform:uppercase}
.admonitionblock>table td.content{padding-left:1.125em;padding-right:1.25em;border-left:1px solid #dddddf;color:rgba(0,0,0,.6)}
.admonitionblock>table td.content>:last-child>:last-child{margin-bottom:0}
.exampleblock>.content{border-style:solid;border-width:1px;border-color:#e6e6e6;margin-bottom:1.25em;padding:1.25em;background:#fff;-webkit-border-radius:4px;border-radius:4px}
.exampleblock>.content>:first-child{margin-top:0}
.exampleblock>.content>:last-child{margin-bottom:0}
.sidebarblock{border-style:solid;border-width:1px;border-color:#dbdbd6;margin-bottom:1.25em;padding:1.25em;background:#f3f3f2;-webkit-border-radius:4px;border-radius:4px}
.sidebarblock>:first-child{margin-top:0}
.sidebarblock>:last-child{margin-bottom:0}
.sidebarblock>.content>.title{color:#7a2518;margin-top:0;text-align:center}
.exampleblock>.content>:last-child>:last-child,.exampleblock>.content .olist>ol>li:last-child>:last-child,.exampleblock>.content .ulist>ul>li:last-child>:last-child,.exampleblock>.content .qlist>ol>li:last-child>:last-child,.sidebarblock>.content>:last-child>:last-child,.sidebarblock>.content .olist>ol>li:last-child>:last-child,.sidebarblock>.content .ulist>ul>li:last-child>:last-child,.sidebarblock>.content .qlist>ol>li:last-child>:last-child{margin-bottom:0}
.literalblock pre,.listingblock>.content>pre{-webkit-border-radius:4px;border-radius:4px;word-wrap:break-word;overflow-x:auto;padding:1em;font-size:.8125em}
@media screen and (min-width:768px){.literalblock pre,.listingblock>.content>pre{font-size:.90625em}}
@media screen and (min-width:1280px){.literalblock pre,.listingblock>.content>pre{font-size:1em}}
.literalblock pre,.listingblock>.content>pre:not(.highlight),.listingblock>.content>pre[class="highlight"],.listingblock>.content>pre[class^="highlight "]{background:#f7f7f8}
.literalblock.output pre{color:#f7f7f8;background:rgba(0,0,0,.9)}
.listingblock>.content{position:relative}
.listingblock code[data-lang]::before{display:none;content:attr(data-lang);position:absolute;font-size:.75em;top:.425rem;right:.5rem;line-height:1;text-transform:uppercase;color:inherit;opacity:.5}
.listingblock:hover code[data-lang]::before{display:block}
.listingblock.terminal pre .command::before{content:attr(data-prompt);padding-right:.5em;color:inherit;opacity:.5}
.listingblock.terminal pre .command:not([data-prompt])::before{content:"$"}
.listingblock pre.highlightjs{padding:0}
.listingblock pre.highlightjs>code{padding:1em;-webkit-border-radius:4px;border-radius:4px}
.listingblock pre.prettyprint{border-width:0}
.prettyprint{background:#f7f7f8}
pre.prettyprint .linenums{line-height:1.45;margin-left:2em}
pre.prettyprint li{background:none;list-style-type:inherit;padding-left:0}
pre.prettyprint li code[data-lang]::before{opacity:1}
pre.prettyprint li:not(:first-child) code[data-lang]::before{display:none}
table.linenotable{border-collapse:separate;border:0;margin-bottom:0;background:none}
table.linenotable td[class]{color:inherit;vertical-align:top;padding:0;line-height:inherit;white-space:normal}
table.linenotable td.code{padding-left:.75em}
table.linenotable td.linenos{border-right:1px solid currentColor;opacity:.35;padding-right:.5em}
pre.pygments .lineno{border-right:1px solid currentColor;opacity:.35;display:inline-block;margin-right:.75em}
pre.pygments .lineno::before{content:"";margin-right:-.125em}
.quoteblock{margin:0 1em 1.25em 1.5em;display:table}
.quoteblock:not(.excerpt)>.title{margin-left:-1.5em;margin-bottom:.75em}
.quoteblock blockquote,.quoteblock p{color:rgba(0,0,0,.85);font-size:1.15rem;line-height:1.75;word-spacing:.1em;letter-spacing:0;font-style:italic;text-align:justify}
.quoteblock blockquote{margin:0;padding:0;border:0}
.quoteblock blockquote::before{content:"\201c";float:left;font-size:2.75em;font-weight:bold;line-height:.6em;margin-left:-.6em;color:#7a2518;text-shadow:0 1px 2px rgba(0,0,0,.1)}
.quoteblock blockquote>.paragraph:last-child p{margin-bottom:0}
.quoteblock .attribution{margin-top:.75em;margin-right:.5ex;text-align:right}
.verseblock{margin:0 1em 1.25em}
.verseblock pre{font-family:"Open Sans","DejaVu Sans",sans;font-size:1.15rem;color:rgba(0,0,0,.85);font-weight:300;text-rendering:optimizeLegibility}
.verseblock pre strong{font-weight:400}
.verseblock .attribution{margin-top:1.25rem;margin-left:.5ex}
.quoteblock .attribution,.verseblock .attribution{font-size:.9375em;line-height:1.45;font-style:italic}
.quoteblock .attribution br,.verseblock .attribution br{display:none}
.quoteblock .attribution cite,.verseblock .attribution cite{display:block;letter-spacing:-.025em;color:rgba(0,0,0,.6)}
.quoteblock.abstract blockquote::before,.quoteblock.excerpt blockquote::before,.quoteblock .quoteblock blockquote::before{display:none}
.quoteblock.abstract blockquote,.quoteblock.abstract p,.quoteblock.excerpt blockquote,.quoteblock.excerpt p,.quoteblock .quoteblock blockquote,.quoteblock .quoteblock p{line-height:1.6;word-spacing:0}
.quoteblock.abstract{margin:0 1em 1.25em;display:block}
.quoteblock.abstract>.title{margin:0 0 .375em;font-size:1.15em;text-align:center}
.quoteblock.excerpt>blockquote,.quoteblock .quoteblock{padding:0 0 .25em 1em;border-left:.25em solid #dddddf}
.quoteblock.excerpt,.quoteblock .quoteblock{margin-left:0}
.quoteblock.excerpt blockquote,.quoteblock.excerpt p,.quoteblock .quoteblock blockquote,.quoteblock .quoteblock p{color:inherit;font-size:1.0625rem}
.quoteblock.excerpt .attribution,.quoteblock .quoteblock .attribution{color:inherit;text-align:left;margin-right:0}
table.tableblock{max-width:100%;border-collapse:separate}
p.tableblock:last-child{margin-bottom:0}
td.tableblock>.content>:last-child{margin-bottom:-1.25em}
td.tableblock>.content>:last-child.sidebarblock{margin-bottom:0}
table.tableblock,th.tableblock,td.tableblock{border:0 solid #dedede}
table.grid-all>thead>tr>.tableblock,table.grid-all>tbody>tr>.tableblock{border-width:0 1px 1px 0}
table.grid-all>tfoot>tr>.tableblock{border-width:1px 1px 0 0}
table.grid-cols>*>tr>.tableblock{border-width:0 1px 0 0}
table.grid-rows>thead>tr>.tableblock,table.grid-rows>tbody>tr>.tableblock{border-width:0 0 1px}
table.grid-rows>tfoot>tr>.tableblock{border-width:1px 0 0}
table.grid-all>*>tr>.tableblock:last-child,table.grid-cols>*>tr>.tableblock:last-child{border-right-width:0}
table.grid-all>tbody>tr:last-child>.tableblock,table.grid-all>thead:last-child>tr>.tableblock,table.grid-rows>tbody>tr:last-child>.tableblock,table.grid-rows>thead:last-child>tr>.tableblock{border-bottom-width:0}
table.frame-all{border-width:1px}
table.frame-sides{border-width:0 1px}
table.frame-topbot,table.frame-ends{border-width:1px 0}
table.stripes-all tr,table.stripes-odd tr:nth-of-type(odd),table.stripes-even tr:nth-of-type(even),table.stripes-hover tr:hover{background:#f8f8f7}
th.halign-left,td.halign-left{text-align:left}
th.halign-right,td.halign-right{text-align:right}
th.halign-center,td.halign-center{text-align:center}
th.valign-top,td.valign-top{vertical-align:top}
th.valign-bottom,td.valign-bottom{vertical-align:bottom}
th.valign-middle,td.valign-middle{vertical-align:middle}
table thead th,table tfoot th{font-weight:bold}
tbody tr th{display:table-cell;line-height:1.6;background:#f7f8f7}
tbody tr th,tbody tr th p,tfoot tr th,tfoot tr th p{color:rgba(0,0,0,.8);font-weight:bold}
p.tableblock>code:only-child{background:none;padding:0}
p.tableblock{font-size:1em}
ol{margin-left:1.75em}
ul li ol{margin-left:1.5em}
dl dd{margin-left:1.125em}
dl dd:last-child,dl dd:last-child>:last-child{margin-bottom:0}
ol>li p,ul>li p,ul dd,ol dd,.olist .olist,.ulist .ulist,.ulist .olist,.olist .ulist{margin-bottom:.625em}
ul.checklist,ul.none,ol.none,ul.no-bullet,ol.no-bullet,ol.unnumbered,ul.unstyled,ol.unstyled{list-style-type:none}
ul.no-bullet,ol.no-bullet,ol.unnumbered{margin-left:.625em}
ul.unstyled,ol.unstyled{margin-left:0}
ul.checklist{margin-left:.625em}
ul.checklist li>p:first-child>.fa-square-o:first-child,ul.checklist li>p:first-child>.fa-check-square-o:first-child{width:1.25em;font-size:.8em;position:relative;bottom:.125em}
ul.checklist li>p:first-child>input[type="checkbox"]:first-child{margin-right:.25em}
ul.inline{display:-ms-flexbox;display:-webkit-box;display:flex;-ms-flex-flow:row wrap;-webkit-flex-flow:row wrap;flex-flow:row wrap;list-style:none;margin:0 0 .625em -1.25em}
ul.inline>li{margin-left:1.25em}
.unstyled dl dt{font-weight:400;font-style:normal}
ol.arabic{list-style-type:decimal}
ol.decimal{list-style-type:decimal-leading-zero}
ol.loweralpha{list-style-type:lower-alpha}
ol.upperalpha{list-style-type:upper-alpha}
ol.lowerroman{list-style-type:lower-roman}
ol.upperroman{list-style-type:upper-roman}
ol.lowergreek{list-style-type:lower-greek}
.hdlist>table,.colist>table{border:0;background:none}
.hdlist>table>tbody>tr,.colist>table>tbody>tr{background:none}
td.hdlist1,td.hdlist2{vertical-align:top;padding:0 .625em}
td.hdlist1{font-weight:bold;padding-bottom:1.25em}
.literalblock+.colist,.listingblock+.colist{margin-top:-.5em}
.colist td:not([class]):first-child{padding:.4em .75em 0;line-height:1;vertical-align:top}
.colist td:not([class]):first-child img{max-width:none}
.colist td:not([class]):last-child{padding:.25em 0}
.thumb,.th{line-height:0;display:inline-block;border:solid 4px #fff;-webkit-box-shadow:0 0 0 1px #ddd;box-shadow:0 0 0 1px #ddd}
.imageblock.left{margin:.25em .625em 1.25em 0}
.imageblock.right{margin:.25em 0 1.25em .625em}
.imageblock>.title{margin-bottom:0}
.imageblock.thumb,.imageblock.th{border-width:6px}
.imageblock.thumb>.title,.imageblock.th>.title{padding:0 .125em}
.image.left,.image.right{margin-top:.25em;margin-bottom:.25em;display:inline-block;line-height:0}
.image.left{margin-right:.625em}
.image.right{margin-left:.625em}
a.image{text-decoration:none;display:inline-block}
a.image object{pointer-events:none}
sup.footnote,sup.footnoteref{font-size:.875em;position:static;vertical-align:super}
sup.footnote a,sup.footnoteref a{text-decoration:none}
sup.footnote a:active,sup.footnoteref a:active{text-decoration:underline}
#footnotes{padding-top:.75em;padding-bottom:.75em;margin-bottom:.625em}
#footnotes hr{width:20%;min-width:6.25em;margin:-.25em 0 .75em;border-width:1px 0 0}
#footnotes .footnote{padding:0 .375em 0 .225em;line-height:1.3334;font-size:.875em;margin-left:1.2em;margin-bottom:.2em}
#footnotes .footnote a:first-of-type{font-weight:bold;text-decoration:none;margin-left:-1.05em}
#footnotes .footnote:last-of-type{margin-bottom:0}
#content #footnotes{margin-top:-.625em;margin-bottom:0;padding:.75em 0}
.gist .file-data>table{border:0;background:#fff;width:100%;margin-bottom:0}
.gist .file-data>table td.line-data{width:99%}
div.unbreakable{page-break-inside:avoid}
.big{font-size:larger}
.small{font-size:smaller}
.underline{text-decoration:underline}
.overline{text-decoration:overline}
.line-through{text-decoration:line-through}
.aqua{color:#00bfbf}
.aqua-background{background:#00fafa}
.black{color:#000}
.black-background{background:#000}
.blue{color:#0000bf}
.blue-background{background:#0000fa}
.fuchsia{color:#bf00bf}
.fuchsia-background{background:#fa00fa}
.gray{color:#606060}
.gray-background{background:#7d7d7d}
.green{color:#006000}
.green-background{background:#007d00}
.lime{color:#00bf00}
.lime-background{background:#00fa00}
.maroon{color:#600000}
.maroon-background{background:#7d0000}
.navy{color:#000060}
.navy-background{background:#00007d}
.olive{color:#606000}
.olive-background{background:#7d7d00}
.purple{color:#600060}
.purple-background{background:#7d007d}
.red{color:#bf0000}
.red-background{background:#fa0000}
.silver{color:#909090}
.silver-background{background:#bcbcbc}
.teal{color:#006060}
.teal-background{background:#007d7d}
.white{color:#bfbfbf}
.white-background{background:#fafafa}
.yellow{color:#bfbf00}
.yellow-background{background:#fafa00}
span.icon>.fa{cursor:default}
a span.icon>.fa{cursor:inherit}
.admonitionblock td.icon [class^="fa icon-"]{font-size:2.5em;text-shadow:1px 1px 2px rgba(0,0,0,.5);cursor:default}
.admonitionblock td.icon .icon-note::before{content:"\f05a";color:#19407c}
.admonitionblock td.icon .icon-tip::before{content:"\f0eb";text-shadow:1px 1px 2px rgba(155,155,0,.8);color:#111}
.admonitionblock td.icon .icon-warning::before{content:"\f071";color:#bf6900}
.admonitionblock td.icon .icon-caution::before{content:"\f06d";color:#bf3400}
.admonitionblock td.icon .icon-important::before{content:"\f06a";color:#bf0000}
.conum[data-value]{display:inline-block;color:#fff!important;background:rgba(0,0,0,.8);-webkit-border-radius:100px;border-radius:100px;text-align:center;font-size:.75em;width:1.67em;height:1.67em;line-height:1.67em;font-family:"Open Sans","DejaVu Sans",sans-serif;font-style:normal;font-weight:bold}
.conum[data-value] *{color:#fff!important}
.conum[data-value]+b{display:none}
.conum[data-value]::after{content:attr(data-value)}
pre .conum[data-value]{position:relative;top:-.125em}
b.conum *{color:inherit!important}
.conum:not([data-value]):empty{display:none}
dt,th.tableblock,td.content,div.footnote{text-rendering:optimizeLegibility}
h1,h2,p,td.content,span.alt{letter-spacing:-.01em}
p strong,td.content strong,div.footnote strong{letter-spacing:-.005em}
p,blockquote,dt,td.content,span.alt{font-size:1.0625rem}
p{margin-bottom:1.25rem}
.sidebarblock p,.sidebarblock dt,.sidebarblock td.content,p.tableblock{font-size:1em}
.exampleblock>.content{background:#fffef7;border-color:#e0e0dc;-webkit-box-shadow:0 1px 4px #e0e0dc;box-shadow:0 1px 4px #e0e0dc}
.print-only{display:none!important}
@page{margin:1.25cm .75cm}
@media print{*{-webkit-box-shadow:none!important;box-shadow:none!important;text-shadow:none!important}
html{font-size:80%}
a{color:inherit!important;text-decoration:underline!important}
a.bare,a[href^="#"],a[href^="mailto:"]{text-decoration:none!important}
a[href^="http:"]:not(.bare)::after,a[href^="https:"]:not(.bare)::after{content:"(" attr(href) ")";display:inline-block;font-size:.875em;padding-left:.25em}
abbr[title]::after{content:" (" attr(title) ")"}
pre,blockquote,tr,img,object,svg{page-break-inside:avoid}
thead{display:table-header-group}
svg{max-width:100%}
p,blockquote,dt,td.content{font-size:1em;orphans:3;widows:3}
h2,h3,#toctitle,.sidebarblock>.content>.title{page-break-after:avoid}
#toc,.sidebarblock,.exampleblock>.content{background:none!important}
#toc{border-bottom:1px solid #dddddf!important;padding-bottom:0!important}
body.book #header{text-align:center}
body.book #header>h1:first-child{border:0!important;margin:2.5em 0 1em}
body.book #header .details{border:0!important;display:block;padding:0!important}
body.book #header .details span:first-child{margin-left:0!important}
body.book #header .details br{display:block}
body.book #header .details br+span::before{content:none!important}
body.book #toc{border:0!important;text-align:left!important;padding:0!important;margin:0!important}
body.book #toc,body.book #preamble,body.book h1.sect0,body.book .sect1>h2{page-break-before:always}
.listingblock code[data-lang]::before{display:block}
#footer{padding:0 .9375em}
.hide-on-print{display:none!important}
.print-only{display:block!important}
.hide-for-print{display:none!important}
.show-for-print{display:inherit!important}}
@media print,amzn-kf8{#header>h1:first-child{margin-top:1.25rem}
.sect1{padding:0!important}
.sect1+.sect1{border:0}
#footer{background:none}
#footer-text{color:rgba(0,0,0,.6);font-size:.9em}}
@media amzn-kf8{#header,#content,#footnotes,#footer{padding:0}}
</style>
</head>
<body class="book toc2 toc-left">
<div id="header">
<h1>Nesonnechek API Document</h1>
<div class="details">
<span id="revnumber">version 1.0.0-SNAPSHOT</span>
</div>
<div id="toc" class="toc2">
<div id="toctitle">Table of Contents</div>
<ul class="sectlevel1">
<li><a href="#auth">Authentication Rest API</a>
<ul class="sectlevel2">
<li><a href="#_sign_in">Sign in</a>
<ul class="sectlevel3">
<li><a href="#_request">Request</a></li>
<li><a href="#_response">Response</a></li>
</ul>
</li>
<li><a href="#_sign_up">Sign up</a>
<ul class="sectlevel3">
<li><a href="#_request_2">Request</a></li>
<li><a href="#_response_2">Response</a></li>
</ul>
</li>
</ul>
</li>
<li><a href="#user">User Rest API</a>
<ul class="sectlevel2">
<li><a href="#_get_account_info">Get account info</a>
<ul class="sectlevel3">
<li><a href="#_request_3">Request</a></li>
<li><a href="#_response_3">Response</a></li>
</ul>
</li>
<li><a href="#_update_account_info">Update account info</a>
<ul class="sectlevel3">
<li><a href="#_request_4">Request</a></li>
<li><a href="#_response_4">Response</a></li>
</ul>
</li>
<li><a href="#_delete_account">Delete account</a>
<ul class="sectlevel3">
<li><a href="#_request_5">Request</a></li>
<li><a href="#_response_5">Response</a></li>
</ul>
</li>
<li><a href="#_get_accounts_ebook_infos">Get account&#8217;s ebook infos</a>
<ul class="sectlevel3">
<li><a href="#_response_6">Response</a></li>
</ul>
</li>
<li><a href="#_delete_accounts_ebook_infos">Delete account&#8217;s ebook infos</a>
<ul class="sectlevel3">
<li><a href="#_request_6">Request</a></li>
<li><a href="#_response_7">Response</a></li>
</ul>
</li>
</ul>
</li>
<li><a href="#ebook">Ebook Rest API</a>
<ul class="sectlevel2">
<li><a href="#_create_txt_ebook">Create txt ebook</a>
<ul class="sectlevel3">
<li><a href="#_request_7">Request</a></li>
<li><a href="#_response_8">Response</a></li>
</ul>
</li>
<li><a href="#_get_ebook_info">Get ebook info</a>
<ul class="sectlevel3">
<li><a href="#_request_8">Request</a></li>
<li><a href="#_response_9">Response</a></li>
</ul>
</li>
<li><a href="#_download_ebook_file">Download Ebook file</a>
<ul class="sectlevel3">
<li><a href="#_request_9">Request</a></li>
<li><a href="#_response_10">Response</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<div class="sect1">
<h2 id="auth"><a class="link" href="#auth">Authentication Rest API</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_sign_in"><a class="link" href="#_sign_in">Sign in</a></h3>
<div class="sect3">
<h4 id="_request"><a class="link" href="#_request">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/auth/sign-in' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "email" : "user1@email.com",
  "password" : "user1_password"
}'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">POST /auth/sign-in HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 69
Host: api.nesonnechek.com

{
  "email" : "user1@email.com",
  "password" : "user1_password"
}</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response"><a class="link" href="#_response">Response</a></h4>
<div class="admonitionblock important">
<table>
<tr>
<td class="icon">
<div class="title">Important</div>
</td>
<td class="content">
It is not possible to request the same response as this, and it is not stored in a separate storage space.
</td>
</tr>
</table>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>reqHeaders</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Request headers required for API calls</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>authScheme</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Authentication scheme required for API calls</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>accessToken</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">JWT used for API calls</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>refreshToken</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">JWT used for reissuing JWT</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>reissue</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to reissue JWT</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 856

{
  "reqHeaders" : "Authorization",
  "authScheme" : "Bearer",
  "accessToken" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYjNlMTAzMi00NjE0LTQ0ZjUtODhkMy0xMGU5NmQ1OTkyYTUiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.fIgS3vqY0vMKgAzg2R4JMp_y5C6iT22XW-0KXoJgDvw",
  "refreshToken" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3ZDUxNTYzYS0zM2JhLTRlZTctOGRmMS1jNzVhMmE2N2E2NTIiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6InJlZnJlc2giLCJpYXQiOjE3MTEzNzIxNjEsImV4cCI6MTcxMTM3MjIyMSwibmJmIjoxNzExMzcyMTkxfQ.3In-qkTrzR0rMKuIWyPpMkUNbrgap3VOO5px0rjGKL8",
  "_links" : {
    "reissue" : {
      "href" : "http://localhost:8080/api/sign/reissue"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_sign_up"><a class="link" href="#_sign_up">Sign up</a></h3>
<div class="sect3">
<h4 id="_request_2"><a class="link" href="#_request_2">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/auth/sign-up' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{
  "email" : "new_user@email.com",
  "password" : "new_user_password",
  "username" : "NEW_USER"
}'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">POST /auth/sign-up HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 103
Host: api.nesonnechek.com

{
  "email" : "new_user@email.com",
  "password" : "new_user_password",
  "username" : "NEW_USER"
}</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_2"><a class="link" href="#_response_2">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>email</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Signed up user email</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>username</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Signed up user name</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>loginCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Signed up user login count number</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Signed up user creation date</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sign-in</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to sign in</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 201 Created
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 394

{
  "email" : "new_user@email.com",
  "username" : "NEW_USER",
  "loginCount" : 0,
  "createdAt" : "2024-03-25T22:09:21.8127803",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/account"
    },
    "sign-in" : {
      "href" : "http://localhost:8080/auth/sign-in"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="user"><a class="link" href="#user">User Rest API</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_get_account_info"><a class="link" href="#_get_account_info">Get account info</a></h3>
<div class="sect3">
<h4 id="_request_3"><a class="link" href="#_request_3">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/users/account' -i -X GET \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzYjhlNWFiMi0wYjJlLTRhZTMtODk1Yi04YWYzMWUwMzkyMDAiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.1kdFAbySGiUItnCtvyXU9uxvc11h0_z42yGL__6mzfk'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">GET /users/account HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzYjhlNWFiMi0wYjJlLTRhZTMtODk1Yi04YWYzMWUwMzkyMDAiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.1kdFAbySGiUItnCtvyXU9uxvc11h0_z42yGL__6mzfk
Host: api.nesonnechek.com</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_3"><a class="link" href="#_response_3">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>email</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User email</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>username</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User name</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>loginCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User login count number</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>lastLoginAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Last login date</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Account creation date</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sign-in</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to sign in</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 362

{
  "email" : "user1@email.com",
  "username" : "USER1_NEW_USERNAME",
  "loginCount" : 4,
  "lastLoginAt" : "2024-03-25T22:09:21.904321",
  "createdAt" : "2022-08-20T12:00:00",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/account"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_update_account_info"><a class="link" href="#_update_account_info">Update account info</a></h3>
<div class="sect3">
<h4 id="_request_4"><a class="link" href="#_request_4">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/users/account' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiMjc2Njg4MC1kOTBiLTQzZmItYmRiMS04MmRjNTU5N2U5YWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.2R-zAEEB0M2vn4Ej4Lg9v5rV47JDN4NFXUh-5H0VFso' \
    -d '{
  "password" : "user1_new_password",
  "username" : "USER1_NEW_USERNAME"
}'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">PUT /users/account HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiMjc2Njg4MC1kOTBiLTQzZmItYmRiMS04MmRjNTU5N2U5YWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.2R-zAEEB0M2vn4Ej4Lg9v5rV47JDN4NFXUh-5H0VFso
Content-Length: 79
Host: api.nesonnechek.com

{
  "password" : "user1_new_password",
  "username" : "USER1_NEW_USERNAME"
}</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_4"><a class="link" href="#_response_4">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>email</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User email</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>username</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User name</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>loginCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">User login count number</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>lastLoginAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Last login date</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Account creation date</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sign-in</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to sign in</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 362

{
  "email" : "user1@email.com",
  "username" : "USER1_NEW_USERNAME",
  "loginCount" : 4,
  "lastLoginAt" : "2024-03-25T22:09:21.904321",
  "createdAt" : "2022-08-20T12:00:00",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/account"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_account"><a class="link" href="#_delete_account">Delete account</a></h3>
<div class="sect3">
<h4 id="_request_5"><a class="link" href="#_request_5">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/users/account' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNDI0OGE5MC1hMTI2LTQ1NDQtODhkMS1jMmI4YTgwMmY1YzQiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXI1QGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.SKbouDRoDx4XyaBCKU7IXx6-EwcLega5Goa6bJ8ArMg' \
    -d '{
  "password" : "user5_password"
}'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">DELETE /users/account HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNDI0OGE5MC1hMTI2LTQ1NDQtODhkMS1jMmI4YTgwMmY1YzQiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXI1QGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.SKbouDRoDx4XyaBCKU7IXx6-EwcLega5Goa6bJ8ArMg
Content-Length: 37
Host: api.nesonnechek.com

{
  "password" : "user5_password"
}</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_5"><a class="link" href="#_response_5">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_get_accounts_ebook_infos"><a class="link" href="#_get_accounts_ebook_infos">Get account&#8217;s ebook infos</a></h3>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/users/account/ebooks' -i -X GET \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODVlZTViNS0yN2UwLTQyN2EtOGJhOS02NTIwODJkNTdkOWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.kVvabVp0jP6axmdhhbmEnWnGGqLfU7VMEb0j7joX3vY'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">GET /users/account/ebooks HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODVlZTViNS0yN2UwLTQyN2EtOGJhOS02NTIwODJkNTdkOWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MiwiZXhwIjoxNzExMzcyMTkyfQ.kVvabVp0jP6axmdhhbmEnWnGGqLfU7VMEb0j7joX3vY
Host: api.nesonnechek.com</code></pre>
</div>
</div>
<div class="sect3">
<h4 id="_response_6"><a class="link" href="#_response_6">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].uuid</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook UUID</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].name</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook title</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].type</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file type</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].extension</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file extension</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].downloadCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file download count</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Created date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].expiredAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Expire date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[].owner</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook owner</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_embedded.ebooks.[]._links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>page</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">PAGE link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>download</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to download ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sign-in</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to sign in</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>first</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The first page of results</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>last</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The last page of results</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>next</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The next page of results</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>prev</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The previous page of results</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 2000

{
  "_embedded" : {
    "ebooks" : [ {
      "uuid" : "550e8400-e29b-41d4-a716-446655440001",
      "name" : "ebook-1",
      "type" : "epub2",
      "extension" : ".epub",
      "downloadCount" : 3,
      "createdAt" : "2022-11-29T12:00:00",
      "expiredAt" : "2022-12-29T12:00:00",
      "owner" : "USER1_NEW_USERNAME",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/ebooks/550e8400-e29b-41d4-a716-446655440001"
        }
      }
    }, {
      "uuid" : "994b4bd3-b1f8-4782-b1ea-9cef1d760dc3",
      "name" : "Lorem Ipsum",
      "type" : "epub2",
      "extension" : ".epub",
      "downloadCount" : 0,
      "createdAt" : "2024-03-25T22:09:20.961203",
      "expiredAt" : "2024-04-24T22:09:20.961203",
      "owner" : "USER1_NEW_USERNAME",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/ebooks/994b4bd3-b1f8-4782-b1ea-9cef1d760dc3"
        },
        "download" : {
          "href" : "http://localhost:8080/ebooks/994b4bd3-b1f8-4782-b1ea-9cef1d760dc3/file"
        }
      }
    }, {
      "uuid" : "a5f38e35-0bf5-47f3-8bf3-9a34c3dd4daa",
      "name" : "Lorem Ipsum",
      "type" : "epub2",
      "extension" : ".epub",
      "downloadCount" : 1,
      "createdAt" : "2024-03-25T22:09:21.322372",
      "expiredAt" : "2024-04-24T22:09:21.322372",
      "owner" : "USER1_NEW_USERNAME",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/ebooks/a5f38e35-0bf5-47f3-8bf3-9a34c3dd4daa"
        },
        "download" : {
          "href" : "http://localhost:8080/ebooks/a5f38e35-0bf5-47f3-8bf3-9a34c3dd4daa/file"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/account/ebooks?page=0&amp;size=20"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 3,
    "totalPages" : 1,
    "number" : 0
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_accounts_ebook_infos"><a class="link" href="#_delete_accounts_ebook_infos">Delete account&#8217;s ebook infos</a></h3>
<div class="sect3">
<h4 id="_request_6"><a class="link" href="#_request_6">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/users/account/ebooks' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkODE0YjI0NS02MzBiLTQwZjctOTU3YS0zNDVkYzdiMDZkMDkiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIyQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.2TAGusP0vPYywDQF98BWgCsamCz-UO94yCkRqpEPXjk' \
    -d '{
  "password" : "user2_password"
}'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">DELETE /users/account/ebooks HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkODE0YjI0NS02MzBiLTQwZjctOTU3YS0zNDVkYzdiMDZkMDkiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIyQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.2TAGusP0vPYywDQF98BWgCsamCz-UO94yCkRqpEPXjk
Content-Length: 37
Host: api.nesonnechek.com

{
  "password" : "user2_password"
}</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_7"><a class="link" href="#_response_7">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="ebook"><a class="link" href="#ebook">Ebook Rest API</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_create_txt_ebook"><a class="link" href="#_create_txt_ebook">Create txt ebook</a></h3>
<div class="sect3">
<h4 id="_request_7"><a class="link" href="#_request_7">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/ebooks/txt-ebook' -i -X POST \
    -H 'Content-Type: multipart/form-data;charset=UTF-8' \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0MGUxNjkzMS0zYjc5LTQ3MmUtYTM5Ny0xMTJkYzFhNjg5NWQiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MCwiZXhwIjoxNzExMzcyMTkwfQ.BPxqL-IdUd-4C0hdyb56lEBI7ioQTQSEHHhVsUQCfwo' \
    -F 'txtFile=@lorem-ipsum.txt;type=text/plain' \
    -F 'type=epub2'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">POST /ebooks/txt-ebook HTTP/1.1
Content-Type: multipart/form-data;charset=UTF-8; boundary=6o2knFse3p53ty9dmcQvWAIx1zInP11uCfbm
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0MGUxNjkzMS0zYjc5LTQ3MmUtYTM5Ny0xMTJkYzFhNjg5NWQiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MCwiZXhwIjoxNzExMzcyMTkwfQ.BPxqL-IdUd-4C0hdyb56lEBI7ioQTQSEHHhVsUQCfwo
Host: api.nesonnechek.com

--6o2knFse3p53ty9dmcQvWAIx1zInP11uCfbm
Content-Disposition: form-data; name=type

epub2
--6o2knFse3p53ty9dmcQvWAIx1zInP11uCfbm
Content-Disposition: form-data; name=txtFile; filename=lorem-ipsum.txt
Content-Type: text/plain

*BT*Lorem Ipsum
*BA*John Doe
*ST*What is Lorem Ipsum?
Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
*ST*Where does it come from?
Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.
*ST*Why do we use it?
It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
*ST*Where can I get some?
There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.

--6o2knFse3p53ty9dmcQvWAIx1zInP11uCfbm--</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_8"><a class="link" href="#_response_8">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>uuid</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook UUID</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>name</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook title</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>type</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file type</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>extension</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file extension</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>downloadCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file download count</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Created date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>expiredAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Expire date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>owner</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook owner</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>download</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to download ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 201 Created
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 597

{
  "uuid" : "994b4bd3-b1f8-4782-b1ea-9cef1d760dc3",
  "name" : "Lorem Ipsum",
  "type" : "epub2",
  "extension" : ".epub",
  "downloadCount" : 0,
  "createdAt" : "2024-03-25T22:09:20.9612033",
  "expiredAt" : "2024-04-24T22:09:20.9612033",
  "owner" : "USER1",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/ebooks/994b4bd3-b1f8-4782-b1ea-9cef1d760dc3"
    },
    "download" : {
      "href" : "http://localhost:8080/ebooks/994b4bd3-b1f8-4782-b1ea-9cef1d760dc3/file"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_get_ebook_info"><a class="link" href="#_get_ebook_info">Get ebook info</a></h3>
<div class="sect3">
<h4 id="_request_8"><a class="link" href="#_request_8">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/ebooks/550e8400-e29b-41d4-a716-446655440001' -i -X GET \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhNTdhNzgyNC1mOWI1LTQxM2YtODI2Ny05M2JiNmNiNGQwZTEiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.rEcbonOEDHjrYLBSaafpPszZ1y28-p05-LJUu7oCI98'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">GET /ebooks/550e8400-e29b-41d4-a716-446655440001 HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhNTdhNzgyNC1mOWI1LTQxM2YtODI2Ny05M2JiNmNiNGQwZTEiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.rEcbonOEDHjrYLBSaafpPszZ1y28-p05-LJUu7oCI98
Host: api.nesonnechek.com</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_9"><a class="link" href="#_response_9">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response Fields</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>uuid</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook UUID</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>name</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook title</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>type</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file type</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>extension</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file extension</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>downloadCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook file download count</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Created date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>expiredAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Expire date of Ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>owner</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Ebook owner</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">HATEOAS link</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HATEOAS Links</dt>
</dl>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Relation</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>self</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>download</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to download ebook file</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>docs</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Link to API documentation</p></td>
</tr>
</tbody>
</table>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 460

{
  "uuid" : "550e8400-e29b-41d4-a716-446655440001",
  "name" : "ebook-1",
  "type" : "epub2",
  "extension" : ".epub",
  "downloadCount" : 3,
  "createdAt" : "2022-11-29T12:00:00",
  "expiredAt" : "2022-12-29T12:00:00",
  "owner" : "USER1",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/ebooks/550e8400-e29b-41d4-a716-446655440001"
    },
    "docs" : {
      "href" : "http://localhost:8080/docs/index.html"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_download_ebook_file"><a class="link" href="#_download_ebook_file">Download Ebook file</a></h3>
<div class="sect3">
<h4 id="_request_9"><a class="link" href="#_request_9">Request</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">CURL</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://api.nesonnechek.com/ebooks/a5f38e35-0bf5-47f3-8bf3-9a34c3dd4daa/file' -i -X GET \
    -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlNjZlMDJhNi1hYjg0LTQ0M2UtYjNmNS04ZTVhMzBjODczYWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.MJTFiM2j1vrNnY-odreGDmelatLG-yxwfw9OywuEyR0'</code></pre>
</div>
</div>
<div class="dlist">
<dl>
<dt class="hdlist1">Request HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">GET /ebooks/a5f38e35-0bf5-47f3-8bf3-9a34c3dd4daa/file HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlNjZlMDJhNi1hYjg0LTQ0M2UtYjNmNS04ZTVhMzBjODczYWYiLCJpc3MiOiJuZXNvbm5lY2hlayIsInN1YiI6InVzZXIxQGVtYWlsLmNvbSIsInB1ciI6ImFjY2VzcyIsImlhdCI6MTcxMTM3MjE2MSwiZXhwIjoxNzExMzcyMTkxfQ.MJTFiM2j1vrNnY-odreGDmelatLG-yxwfw9OywuEyR0
Host: api.nesonnechek.com</code></pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="_response_10"><a class="link" href="#_response_10">Response</a></h4>
<div class="dlist">
<dl>
<dt class="hdlist1">Response HTTP Example</dt>
</dl>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Disposition: attachment; filename*=UTF-8''Lorem%20Ipsum.epub
Content-Type: application/epub+zip;charset=UTF-8
Content-Length: 4621
Accept-Ranges: bytes
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY

PK
    *�yXoa�,         mimetypeapplication/epub+zipPK  *�yX               META-INF/container.xmlU�M� ���d���[mb�ZO�t�D`P������|y?ߓ��;���-������B�6`��X��`JA��6��=fQ���a 3yE|bb��Y�DTF�0W�Ul��k�.7���t�s�nl(��&lt;V��Q���Y�K}�	/1ך��+�+���?�~�PK�!�՟   �   PK  *�yX               OEBPS/chapter4.xhtmlmS�N�0=���ROK�¡�v��ԕ�-���=:�$68vdO6����Y� "Er�yo潙Y^�Z[�A;{�����VNjۜe=�G���Ã�۫����7נ�7���W�����eQ\m��Ϸ��5��yQ\���d���T�0��i�|Sl~�H0�G��9�$3N�i;�d��+��b��@1|�P���gI�N���*�����	�k�b�,���b�/K'�=K�İI��V���kA�Q WC'B���ylaՅ��ڈ��ʞ�TD�9�i%���k� �O��m*j�[ƍ|q�q��[��8^X�Z�vp^��Hg߿۝|�L`���"��(2#�h4nc=9�j]�5�����A�-�n�rdKg(%Wtx��ސ�tؖ�{��JK�IY���L�'�Q_�I��;נ�~8ϞN��e�,��Ц&lt;v(:�km��J��&gt;�\g��36�&gt;�S:$�Z��$�ǧ$/s�=�`.���ma�X��Հ��cXs����*ז��A�b���Խ���I4��mŮq��:F��}ϵO���,RN�Z�#����4�&lt;2�A3�1@��z�&amp;�x�Y���Y��UJx�?����L!U�~I�� �f���PK�ןhb  *  PK  *�yX               OEBPS/chapter2.xhtml��[o1�����@��&amp;-H�Vjh�P	�GǞd�x=�=K��x��rb�H��}�;g��n�1DK�x0*�@��X�&lt;t��18=y�7~t�n2��~
5ˆ�Ϯ.'0د�χ��:��×�����QUM��=���˪Z��������f7�&amp;	�Fi��{i���������l�Mi��FeN�@�1[v����ƀ`#XM�"Ps�W���*+��d�;��NdB��
[`���Ω st\Q�.��5`#xb��i���`�p	����XH&lt;(h-jZ�v*F���+���YƠ��=��I�ZI+��.��p8�-���)���Z�z�gҫB�d�6�c� ��B5�A��ak&lt;nśs����Ɇ��V��Vh�k�|��5B#N��Q'�,��`b!����Q��̬~ɥ�j��,)��:P��{a-��m�$�`�R�ˣ c�N�.-�$�n�j�"uAc�K���LvLs9,z��~�j?���2�%�y�&lt;�@�k��k^OfRy�a)����^n�ݺ�0�P��@��eF���],aVː�%�4,
DD�����ɏd�D��Z��۟Cg���C�A�����D��!���v/�b�-�e�`�#T� �e^Q�Le-A�Ew�\�;;�%qD�(�$���X��Һh��w1z&gt;�E@R�i�*'�ְ&gt;�)&amp;�?FF����b���}��'P2��E�Oa��� �QZ�\�r6d�KUZ�j���wS�t6ַ��.�����tY��T����Ӫ��Vͽ(�U�{�*�[��PK��?�  �  PK  *�yX               OEBPS/chapter3.xhtmlmS���0=Si�a�M{�B�J�+Q�+TX8:ɴ1u��7��3N��&gt;9�yo޼�d�K����I�Q�� uaJ�����a�&gt;Z-o&amp;��������TĀ��v�5D�$y�]'�f������;H�4I�D7�gQE�|H��m��66���%�@���t�K*#.�ezv����?�t&gt;����U(���d$I�p��4�"x� i5d%cZ��,7e724��[�@�2��H�J�
K8���*A�̀Z���td9�Yy�9�'0�0�P��qDh+��nN�20�$Jt�S{�6F�ޅ���Xöq��z��Ad����3�΁6�j#sO&lt;�@���G&lt;F��1�U��_���}�q=��������5ħP�^.�A��U�w�&lt;��^h�݉L���F'n���F`)�ؠ��'uݩ����v^wZ��M{�-*8;v�z���a�����X\(�$�G!(�XG��E�a����p������:�q=Sp���Sgj$Y3^�,ٹ�g�A�mp^I��%�|m���\����M��a5ySyۗ PKJ�	.  �  PK  *�yX               OEBPS/toc.ncx�����0���#.\
&amp;���$j���*msب�O 	ld�&amp;�ӯ!Km��� ۚ�g&gt;��OeO��\�Ȟ��$�s�F��񫳴��(8RPdi�z��M(�ӕ�枷pf�0QGV�uu���xt9��ٕ*e����{�V2�2˄ �!�Ff\�FXRdq����)4	Y�8�K��N���;��&lt;����#Z�n��$H��j��e�����^|׻���� ��.�Rc�Ô&gt;�F��J|����є1�!!�nU�e���,M'��JE%l��)C֭\�Qq�~lt&amp;Ո�&amp;3�����8���4��̅��GV?i/AU���⤺MJ
�͕H2��1{�/�c*�+}3�24�5�փ��B��l�*��n�2]�mg}��k�G�i���rm�$8(9Ye&gt;U����T�F�MM�f��?�!9ӏ#AH��ed�G��nz�T��g�zPK�
�S�  g  PK  *�yX               OEBPS/chapter1.xhtmlmSmo�0�L���#H|ڒ���B�
�I��A�����Xul�wi��9i�"�O�|��=&gt;Og���igo�&lt;'��p��ۛ���էdv{1����q���|�����m1���*˞���~u�WO��&lt;��'�WI��?gY۶i{����V?�C$��9�Ӓ�DDD�gK�n��'�� ���
Uy;YS�lp���\)M�pk�{j��P�+�ـ��]�I��0򐮽�l���� W&gt;h˒([w	�?k[6ġK�������S��7�w���
��ӈ�-�����c���
e��;�Z;��Bvn
���z�⨷FEP��`	��j����&lt;�Ƶ�S�so����{X'�Z�~#G(�r4��uà�ޙA�#�F�guq�%H
Jۘ	����PT�n��[��7FM��T&gt;�0&amp;h5W�1��"�.��l�Bd��Y&gt;������F�1�Zn�$�"z�i�΃o�FS��6�*�4Z��j�`)$O]g�i�/9}��h��y�qֆ!���ѽ�PKm��*  t  PK  *�yX               OEBPS/content.opf���o�0���?X�p����E�JU7)S;,�ɱ?�*؞1K��Ϙ��TS[��(����{J6�ǶA�tB�&lt;H�8@ ��BVy���-\��Tr�(	y U�n��6J�kM�#��|/��^��=�����`r|���c��d�vWs\[�ׄ�Hp]F�Td�7ĝNB��:ݛ�k8#�@��I��`!�K9���=�֗H�K�Aݱ�ar��m�qA�e��t��rf7e����M3�r�qJ7�	���
�@��h�����q��b�Uƻ�8o��1L)i��殺�^�g�4-&lt;�t���PY�n�H�8�O�i&amp;H��H0#��u��������D�P GJ��t$R)J�쵋���À%;bTp[�"�l�ڿzx�֍`�:/r��;�'W�d:�_~%��T[0It��p����E�b�]�6�a���f3l�f�����O�����Gp�.����xs�Y�B��B�=��^�W��1z�x|�;*�PK��I�  �  PK

    *�yXoa�,                       mimetypePK   *�yX�!�՟   �                :   META-INF/container.xmlPK   *�yX�ןhb  *                 OEBPS/chapter4.xhtmlPK   *�yX��?�  �               �  OEBPS/chapter2.xhtmlPK   *�yXJ�	.  �                 OEBPS/chapter3.xhtmlPK   *�yX�
�S�  g               	  OEBPS/toc.ncxPK   *�yXm��*  t               �  OEBPS/chapter1.xhtmlPK   *�yX��I�  �               �  OEBPS/content.opfPK      �  �</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Version 1.0.0-SNAPSHOT<br>
Last updated 2024-03-24 21:31:46 +0900
</div>
</div>
</body>
</html>