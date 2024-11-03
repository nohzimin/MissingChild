//package com.mycom.myapp.domain.child.controller;
//
//import com.mycom.myapp.domain.child.service.ImageUploadService;
//
//import com.mycom.myapp.domain.child.service.MissingChildService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class ImageUploadController {
//
//    private final ImageUploadService imageUploadService;
//    private final MissingChildService missingChildService;
//
//
////    @PostMapping("/image/upload")
////    public String upload(MultipartFile image, Model model) throws IOException {
////        /* 이미지 업로드 로직 */
////        String imageUrl = imageUploadService.upload(image);
////
////        /* View에게 전달할 데이터를 Model에 담음 */
////        model.addAttribute("imageUrl", imageUrl);
////
////        /* View의 이름을 반환하여 View를 렌더링하도록 함 */
////        return "image";
////    }
//
////    @PostMapping("/register")
////    public String registerMissingChild(MissingChildRegisterDto missingChildRegisterDto, MultipartFile image, Model model) throws IOException {
////        // Upload the image and get the URL
////        String imageUrl = imageUploadService.upload(image);
////        missingChildRegisterDto.setPhotoUrl(imageUrl); // Set the photo URL
////
////        // Call the service to insert the missing child
////        MissingChildResultDto result = missingChildService.insertMissingChild(missingChildRegisterDto);
////
////        // Prepare the model for the view or handle the result as needed
////        model.addAttribute("result", result.getResult());
////
////        return "redirect:/success"; // or any view to show success/failure
////    }
//
//}