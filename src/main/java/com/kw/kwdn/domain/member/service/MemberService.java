package com.kw.kwdn.domain.member.service;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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

        String path = "src/main/resources/image/";
        String fileStoredName = "profile/" + member.getId() + "_" + file.getOriginalFilename();
        String photoUrl = path + fileStoredName;
        Path imagePath = Paths.get(photoUrl);

        try {
            Files.write(imagePath, file.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("프로필 저장에 실패하였습니다.");
        }
        member.updateProfileUrl(fileStoredName);
        return member.getPhotoUrl();
    }
}
