package main.java.com.fsts.document_api.Controller;



@RestController
@RequestMapping("/api")
public class DocumentController {

    

    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("document") MultipartFile document) {

        
    }
}
