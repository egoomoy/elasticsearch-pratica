package io.elasticsearch.pratica.crsseq.controller;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.service.impl.CrsseqServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CrsseqController {
    private final CrsseqServiceImpl crsseqService;

    @GetMapping(value = "/loginOut")
    public String loginOut() {
        try {
            crsseqService.rolloverCrsseqDoc();
        } catch (Exception e) {

        }
        return "loginOut";
    }

    @PostMapping("/crsseq")
    public ResponseEntity createCrsseq(@RequestBody CrsseqDTO crsseqDTO) throws Exception {
        crsseqService.saveCrsseq(crsseqDTO);
        return  null;
    }



//
//    @PostMapping("/chat/room")
//    public ResponseMap createRoom(@RequestBody @Valid RoomDto.Request roomDtoReq) throws Exception {
//        final RoomDto.Item rtnRoom = roomService.createRoom(roomDtoReq);
//        RoomDto.Response response = RoomDto.Response.builder()
//                .id(rtnRoom.getId())
//                .roomUuid(rtnRoom.getRoomUuid())
//                .roomNm(rtnRoom.getRoomNm())
//                .isClosed(rtnRoom.getIsClosed())
//                .createDate(rtnRoom.getCreateDate())
//                .status(rtnRoom.getStatus())
//                .build();
//        return new ResponseMap(200, "", response);
//    }
//
//    @GetMapping("/mng/rooms")
//    public ResponseMap getRooms() throws Exception {
//        List<RoomDto.Item> roomList = roomService.selectRoomList();
//        List<RoomDto.Response> responseRoomList = new ArrayList<RoomDto.Response>();
//
//        for (Item rtnRoom : roomList) {
//            ResponseBuilder rb = RoomDto.Response.builder();
//            rb.id(rtnRoom.getId());
//            rb.roomUuid(rtnRoom.getRoomUuid());
//            rb.roomNm(rtnRoom.getRoomNm());
//            rb.isClosed(rtnRoom.getIsClosed());
//            rb.createDate(rtnRoom.getCreateDate());
//            rb.status(rtnRoom.getStatus());
//            if (rtnRoom.getMessage().size() != 0) {
//                rb.lastMessage(rtnRoom.getMessage().get(0).getMessage());
//                rb.lastMessageDate(rtnRoom.getMessage().get(0).getModified_date());
//            }
//            responseRoomList.add(rb.build());
////			}
//        }
//        return new ResponseMap(200, "", responseRoomList);
//    }
//
//    @GetMapping("/mng/room/{roomUUID}")
//    public ResponseMap getRoom(@PathVariable("roomUUID") String id) throws Exception {
//        RoomDto.Item rtnRoom = roomService.findRoomByRoomUuid(UUID.fromString(id));
//        RoomDto.Response response = RoomDto.Response.builder()
//                .id(rtnRoom.getId())
//                .roomUuid(rtnRoom.getRoomUuid())
//                .roomNm(rtnRoom.getRoomNm())
//                .isClosed(rtnRoom.getIsClosed())
//                .createDate(rtnRoom.getCreateDate())
//                .status(rtnRoom.getStatus())
//                .build();
//        return new ResponseMap(200, "", response);
//    }
//
//    @PatchMapping("/mng/room")
//    public ResponseMap updateRoom(@RequestBody @Valid RoomDto.Request roomDtoReq) throws Exception {
//        final RoomDto.Item rtnRoom = roomService.updateRoom(roomDtoReq);
//
//        ResponseBuilder rb = RoomDto.Response.builder();
//        rb.id(rtnRoom.getId());
//        rb.roomUuid(rtnRoom.getRoomUuid());
//        rb.roomNm(rtnRoom.getRoomNm());
//        rb.isClosed(rtnRoom.getIsClosed());
//        rb.createDate(rtnRoom.getCreateDate());
//        rb.status(rtnRoom.getStatus());
//        if (rtnRoom.getMessage().size() != 0) {
//            rb.lastMessage(rtnRoom.getMessage().get(0).getMessage());
//            rb.lastMessageDate(rtnRoom.getMessage().get(0).getModified_date());
//        }
//        RoomDto.Response response = rb.build();
//        return new ResponseMap(200, "", response);
//    }

}
