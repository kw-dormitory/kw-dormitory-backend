package com.kw.kwdn.domain.member.service;

import com.kw.kwdn.domain.image.service.ImageService;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.dto.MemberDetailDTO;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    @Transactional
    public String join(MemberCreateDTO dto) {
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<MemberDTO> findOneById(String userId) {
        return memberRepository.findOneById(userId).map(Member::toDTO);
    }

    public MemberDetailDTO findDetailById(String memberId) {
        return memberRepository
                .findOneById(memberId)
                .map(Member::toDetailDTO)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자 정보가 없습니다."));
    }

    @Transactional
    public String uploadProfileImage(String userId, MultipartFile file) {
        String type = file.getContentType();
        if (type == null || !type.startsWith("image"))
            throw new IllegalArgumentException("올바른 형식의 파일이 아닙니다.");

        Member member = memberRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자 정보가 없습니다."));

        String fileStoredName = "profile/" + member.getId() + "_" + file.getOriginalFilename();

        imageService.save(file, fileStoredName);
        member.updateProfileUrl(fileStoredName);
        return member.getPhotoUrl();
    }
}
