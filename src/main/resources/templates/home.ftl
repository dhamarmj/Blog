<html lang="en"><head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Blog</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>
<body>
<#if userSigned == false>
    <#include "firstNav.ftl">
<#else>
    <#include "navbar.ftl">
</#if>
<br>
<br>
<br>
<!-- Main Content -->
<div class="container">
    <div class="row">

        <div class="col-lg-8 col-md-10 mx-auto">
            <#list list as articulo>
            <div class="post-preview">
                <a href="#">
                    <h2 class="post-title">${articulo.titulo}</h2>
                </a>
                    <#--<h3 class="post-subtitle">${articulo.teaser}</h3>-->
                <#--<p class="post-meta">Posted by-->
                    <#--${autor.usuarioautor.nombre} on ${autor.fecha?string.long}</p>-->
            </div>
            <hr>

            <!-- Pager -->
            <#--<div class="clearfix">-->
                <#--<a class="btn btn-primary float-right" href="#">Older Posts &#8594;</a>-->
            <#--</div>-->
            </#list>
        </div>

    </div>
</div>
<hr><#include "footer.ftl">

<!-- Bootstrap core JavaScript -->
<script src="/jquery/jquery.js"></script>
<script src="/js/bootstrap.bundle.js"></script>

</body></html>
