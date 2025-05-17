//package mealmover.backend.controllers;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.requests.RoleCreateRequestDto;
//import mealmover.backend.dtos.requests.RoleUpdateRequestDto;
//import mealmover.backend.dtos.responses.RoleResponseDto;
//import mealmover.backend.messages.RoleMessages;
//import mealmover.backend.services.RoleService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/roles")
//@RequiredArgsConstructor
//public class RoleController {
//    private final RoleService service;
//    private final RoleMessages roleMessages;
//
//    @PostMapping
//    public ResponseEntity<RoleResponseDto> create(@Valid @RequestBody RoleCreateRequestDto requestDto){
//        RoleResponseDto responseDto = this.service.create(requestDto);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RoleResponseDto>> getAll (){
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RoleResponseDto> getById(@PathVariable UUID id){
//        return ResponseEntity.ok(this.service.getById(id));
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<RoleResponseDto> updateById(
//        @PathVariable UUID id,
//        @Valid @RequestBody RoleUpdateRequestDto requestDto
//    ){
//        //RoleResponseDto responseDto = this.service.updateById(id, requestDto);
//
//        return ResponseEntity.ok(this.service.updateById(id, requestDto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
//        this.service.deleteById(id);
//        return ResponseEntity.ok(roleMessages.deleted());
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteAll(){
//        this.service.deleteAll();
//        return ResponseEntity.ok(roleMessages.deletedAll());
//    }
//
//    @GetMapping("/test/{id}")
//    public ResponseEntity<String> TestMethodByID(@PathVariable UUID id){
//        this.service.TestMethodById(id);
//        return ResponseEntity.ok("This is a test method by ID");
//    }
//
//
//}