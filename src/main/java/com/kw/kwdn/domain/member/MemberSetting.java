package com.kw.kwdn.domain.member;


import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.dto.MemberSettingDTO;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "MEMBER_SETTING")
public class MemberSetting {
    @Id
    @Column(name = "member_setting_id")
    private String id;

    @Column(name = "curfew")
    private Boolean curfew;

    @Column(name = "regular")
    private Boolean regular;

    @Column(name = "notice")
    private Boolean notice;

    // constructor logic
    protected MemberSetting() {
    }

    @Builder
    public MemberSetting(String id, Boolean curfew, Boolean regular, Boolean notice) {
        this.id = id;
        this.curfew = curfew;
        this.regular = regular;
        this.notice = notice;
    }

    public static MemberSetting create(MemberDTO dto) {
        return MemberSetting.builder()
                .id(dto.getId())
                .curfew(false)
                .notice(false)
                .regular(false)
                .build();
    }

    // domain logic
    public MemberSettingDTO toDTO() {
        return MemberSettingDTO.builder()
                .id(id)
                .notice(notice)
                .regular(regular)
                .curfew(curfew)
                .build();
    }

    public void updateCurfew(boolean curfew) {
        this.curfew = curfew;
    }

    public void updateNotice(boolean notice) {
        this.notice = notice;
    }

    public void updateRegular(boolean regular) {
        this.regular = regular;
    }
}
