package com.kw.kwdn.domain.party.dto;

import lombok.Data;

@Data
public class PartySearch {
    // if default value is not setting, search error when active empty search
    private String searchContent = "";
}
