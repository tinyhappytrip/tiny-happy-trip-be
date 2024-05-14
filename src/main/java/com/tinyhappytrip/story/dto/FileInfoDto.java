package com.tinyhappytrip.story.dto;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class FileInfoDto {
    private String saveFolder; // 로컬에 저장하는 위치
    private String originalFile; // 원본 파일명
    private String saveFile; // 저장 파일명
}


//@PostMapping("/write")
//public String write(@ModelAttribute("boardDto") BoardDto boardDto,
//                    @RequestParam(value = "upfile", required = false) MultipartFile[] files, HttpSession session,
//                    RedirectAttributes redirectAttributes) throws Exception {
//    logger.debug("write boardDto : {}", boardDto);
//    MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//    boardDto.setUserId(memberDto.getUserId());
//
////		FileUpload 관련 설정.
//    logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
//    if (!files[0].isEmpty()) {
//        String realPath = servletContext.getRealPath("/upload");
////			String realPath = servletContext.getRealPath("/resources/img");
//        String today = new SimpleDateFormat("yyMMdd").format(new Date());
//        String saveFolder = realPath + File.separator + today;
//        logger.debug("저장 폴더 : {}", saveFolder);
//        File folder = new File(saveFolder);
//        if (!folder.exists())
//            folder.mkdirs();
//
//        List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
//        for (MultipartFile mfile : files) {
//            FileInfoDto fileInfoDto = new FileInfoDto();
//            String originalFileName = mfile.getOriginalFilename();
//            if (!originalFileName.isEmpty()) {
//                String saveFileName = UUID.randomUUID().toString()
//                        + originalFileName.substring(originalFileName.lastIndexOf('.'));
//                fileInfoDto.setSaveFolder(today);
//                fileInfoDto.setOriginalFile(originalFileName);
//                fileInfoDto.setSaveFile(saveFileName);
//                logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
//                mfile.transferTo(new File(folder, saveFileName));
//            }
//            fileInfos.add(fileInfoDto);
//        }
//        boardDto.setFileInfos(fileInfos);
//    }
//
//    boardService.writeArticle(boardDto);
//    redirectAttributes.addAttribute("pgno", "1");
//    redirectAttributes.addAttribute("key", "");
//    redirectAttributes.addAttribute("word", "");
////		redirectAttributes.addFlashAttribute("test", "1234");
//    return "redirect:/article/list";
//}
