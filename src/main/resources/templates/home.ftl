<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Blog</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <!-- Fonts! -->
    <link href="/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800"
          rel="stylesheet" type="text/css">

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
                        <h2 class="post-title">${articulo.getTitulo()}</h2>
                    </a>
                    <p class="post-meta">Posted by ${articulo.getUsuarioautor().getNombre()}
                        on ${articulo.getFecha()?string.long}</p>
                    <h5 class="post-subtitle">${articulo.teaser}</h5>
                    <p class="post-meta"> ${articulo.getStringEtiqueta()}</p>
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

</body>
</html>
