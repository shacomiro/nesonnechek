<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="generator" content="Asciidoctor 2.0.10">
<title>Nesonnechek API Document</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic%7CNoto+Serif:400,400italic,700,700italic%7CDroid+Sans+Mono:400,700">
</head>
<body class="book toc2 toc-left">
<div id="header">
<h1>Nesonnechek API Document</h1>
<div class="details">
<span id="revnumber">version 1.0.0-SNAPSHOT</span>
</div>
<div id="toc" class="toc2">
<h2><div id="toctitle">Table of Contents</div></h2>
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
    *�yXoa�,         mimetypeapplication/epub+zipPK  *�yX               META-INF/container.xmlU�M� ���d���[mb�ZO�t�D`P������|y?ߓ��;���-������B�6`��X
��`JA��6��=fQ���a 3yE|bb��Y�DTF�0W�Ul��k�.7���t�s�nl(��&lt;V��Q���Y�K}�	/1ך��+�+���?�~�PK�!�՟   �   PK  *�yX               OEBPS/chapter4.xhtmlmS�N�0=���ROK�¡�v��ԕ�-���=:�$68vdO6����Y� "Er�yo潙Y^�Z[�A;{�����VNjۜe=�G���Ã�۫����7נ�7���W�����eQ\m��Ϸ��5��yQ\���d���T�0��i�|Sl~�H0�G��9�$3N�i;�d��+��b��@1|�P���gI�N���*�����	�k�b�,���b�/K'�=K�İI��V���kA�Q WC'B
���ylaՅ��ڈ��ʞ�TD�9�i%���k� �O��m*j�[ƍ|q�q��[��8^X�Z�vp^��Hg߿۝|�L`���"��(2#�h4nc=9�j]�5�����A�-�n�rdKg(%Wtx��ސ�tؖ�{��JK�IY���L�'�Q_�I��;נ�~8ϞN��e�,��Ц&lt;v(:�km��J��&gt;�\g��36�&gt;�S:$�Z��$�ǧ$/s�=�`.���ma�X��Հ��cXs����*ז��A�b���Խ���I4��mŮq��:F��}ϵO���,RN�Z�#����4�&lt;2�A3�1@��z�&amp;�x�Y���Y��UJx�?����L!U�~I�� �f���PK�ןhb  *  PK  *�yX               OEBPS/chapter2.xhtml��[o1�����@��&amp;-H�Vjh�P	�GǞd�x=�=K��x��rb�H��}�;g��n�1DK�x0*�@��X�&lt;t��18=y�7~t�n2��~
5ˆ�Ϯ.'0د�χ��:��×�����QUM��=���˪Z��������f7�&amp;	�Fi��{i���������l�Mi��FeN�@�1[v����ƀ`#XM
�"Ps�W���*+��d�;��NdB��
[`���Ω st\Q�.��5`#xb��i���`�p	����XH&lt;(h-jZ�v*F���+���YƠ��=��I�ZI+��.��p8�-���)���Z�z�gҫB�d�6�c� ��B5�A��ak&lt;nśs����Ɇ��V��Vh�k�|��5B#N��Q'�,��`b!����Q��̬~ɥ�j��
,)��:P��{a-��m�$�`�R�ˣ c�N�.-�$�n�j�"uAc�K���LvLs9,z��~�j?���2�%�y�&lt;�@�k��k^OfRy�a)����^n�ݺ�0�P��@��eF���],aVː�%�4,
DD�����
ɏd�D��Z��۟Cg���C�A�����D��!���v/�b�-�e�`�#T� �e^Q�Le-A�Ew�\�;;�%qD�(�$���X��Һh��w1z&gt;�E@R�i�*'�ְ&gt;�)&amp;�?FF����b���}��'P2��E�Oa��� �QZ�\�r6d�KUZ�j���wS�t6ַ��.�����tY��T����Ӫ��Vͽ(�U�{�*�[��PK��?�  �  PK  *�yX               OEBPS/chapter3.xhtmlmS���0=Si�a�M{�B�J�+Q�+TX8:ɴ1u��7��3N��&gt;9�yo޼�d�K����I�Q�� uaJ�����a�&gt;Z-o&amp;��������TĀ��v�5D�$y�]'�f������;H�4I�D7�gQE�|H��m��66���%�@���t�K*#.�ezv����?�t&gt;����U(���d$I�p��4�"x� i5d%cZ��,7e724��[�@�2��H�J�
K8���*A�̀Z���td9�Yy�9�'0�0�P��qDh+��nN�20�$Jt�S{�6F�ޅ���Xöq��z��Ad����3�΁6�j#sO&lt;�@���G&lt;F��1�U��_���}�q=��������5ħP�^.�A��U�w�&lt;��^h�݉L���F'n���F`)�ؠ��'uݩ����v^wZ��M{�-*8;v�z���a�����X\(�$�G!(�XG��E�a����p������:�q=Sp���Sgj$Y3^�,ٹ�g�A�mp^I��%�|m���\����M��a5ySyۗ PKJ�	.  �  PK  *�yX            
   OEBPS/toc.ncx�����0���#.\
&amp;���$j���*msب�O 	ld�&amp;�ӯ!Km��� ۚ�g&gt;��OeO��\�Ȟ��
$�s�F��񫳴��(8RPdi�z��M(�ӕ�枷pf�0QGV�uu���xt9��ٕ*e����{�V2�2˄ �!�Ff\�FXRdq����)4	Y�8�K��N���;��&lt;����#Z�n��$H��j��e�����^|׻���� ��.�Rc�Ô&gt;�F��J|����є1�!!�nU�e���,M'��JE%l��)C֭\�Qq�~lt&amp;Ո�&amp;3�����8���4��̅��GV?i/AU���⤺MJ
�͕H2��1{�/�c*�+}3�24�5�փ��B��l�*��n�2]�mg}��k�G�i���rm�$8(9Ye&gt;U����T�F�MM�f��?�!9ӏ#AH��ed�G��nz�T��g�zPK�
�S�  g  PK  *�yX               OEBPS/chapter1.xhtmlmSmo�0�L���#H|ڒ���B�
�I��A�����Xul�wi��9i�"�O�|��=&gt;Og���igo�&lt;'��p��ۛ���էdv{1����q���|�����m1���*˞���~u�WO��&lt;��'�WI��?gY۶i{����V?�C$��9�Ӓ�DDD�gK�n��'�� ���
Uy;YS�lp���\)M�pk�{j��P�+�ـ��]�I��0򐮽�l���� W&gt;h˒([w	�?k[6ġK�������S��7�w���
��ӈ�-�����c���
e��;�Z;��Bvn
���z�⨷FEP��`	��j����&lt;�Ƶ�S�so����{X'�Z�~#G(�r4��uà�ޙA�#�F�guq�%H
Jۘ
	����PT�n��[��7FM��T&gt;�0&amp;h5W�1��"�
.��l�Bd��Y&gt;������F�1�Zn�$�"z�i�΃o�FS��6�*�4Z��j�`)$O]g�i�/9}��h��y�qֆ!���ѽ�PKm��*  t  PK  *�yX               OEBPS/content.opf���o�0���?X�p����E�JU7)S;,�ɱ?�*؞1K��Ϙ��TS[��(����{J6�ǶA�tB�&lt;H�8@ ��BVy���-\��Tr�(	y U�n��6J�kM�#��|/��^��=�����`r|���c��d�vWs\[�ׄ�Hp]F�Td�7ĝNB��:ݛ�k8#�@��I��`!�K9���=�֗H�K�Aݱ�ar��m�qA�e��t��rf7e����M3�r�qJ7�	���
�@��h�����q��b�Uƻ�8o��1L)i��殺�^�g�4-&lt;�t���PY�n�H�8�O�i&amp;H��H0#��u��������D�P GJ��t$R)J�쵋���À%;bTp[�"�l�ڿzx�֍`�:/r��;�'W�d:�
_~%��T[0It��p����E�b�]�6�a���f3l�f�����O�����Gp�.����xs�Y�B��B�=��^�W��1z�x|�;*�PK��I�  �  PK

    *�yXoa�,                       mimetypePK   *�yX�!�՟   �                :   META-INF/container.xmlPK   *�yX�ןhb  *                 OEBPS/chapter4.xhtmlPK   *�yX��?�  �               �  OEBPS/chapter2.xhtmlPK   *�yXJ�	.  �                 OEBPS/chapter3.xhtmlPK   *�yX�
�S�  g  
             	  OEBPS/toc.ncxPK   *�yXm��*  t               �  OEBPS/chapter1.xhtmlPK   *�yX��I�  �               �
  OEBPS/content.opfPK      �  �</code></pre>
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