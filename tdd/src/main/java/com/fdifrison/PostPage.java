package com.fdifrison;

import java.util.List;

record PostPage(List<Post> posts, long total, long skip, long limit) {}
