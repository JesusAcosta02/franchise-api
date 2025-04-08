package com.franchise_api.domain.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;
}
