package br.com.pp.user.dto

class PageDto <E> (var element: E, var pageCurrent: Long, var pageSize: Long, var total: Long)