package com.alorma.github.libs.contributors.api.dto;

import java.util.List;

public class Contributor {
    public User author;
    public int total;
    public List<Week> weeks;
}