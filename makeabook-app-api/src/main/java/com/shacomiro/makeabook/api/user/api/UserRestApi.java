package com.shacomiro.makeabook.api.user.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserRestApi {
}