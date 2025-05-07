////package com.example.demo.controller;
////
////import com.example.demo.model.RoomsModel;
////import com.example.demo.services.RoomsService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.Map;
////
////@RestController
////@CrossOrigin
////public class RoomsController {
////
////    @Autowired
////    private RoomsService roomsService;
////
////    // Get all rooms
////    @GetMapping("/getAllRooms")
////    public List<RoomsModel> getAllRooms() {
////        return roomsService.getAllRooms();
////    }
////
////    // Get room by ID
////    @GetMapping("/{roomId}")
////    public RoomsModel getRoomById(@PathVariable int roomId) {
////        return roomsService.getRoomById(roomId);
////    }
////
////    // Add a new room
////    @PostMapping("/addRooms")
////    public String addRoom(@RequestBody RoomsModel room) {
////        return roomsService.addRoom(room);
////    }
////    @PutMapping("/update-status/{roomId}")
////    public String updateAvailability( @PathVariable int roomId,@RequestBody Map<String, String> requestBody) {
////
////        String newStatus = requestBody.get("availability_status");
////        boolean result = roomsService.updateRoomAvailability(roomId, newStatus);
////
////        if (result) {
////            return "Room status updated successfully";
////        } else {
////            return "Room not found or update failed";
////        }
////    }
////
////    
////    @PutMapping("/UpdateRoomById{roomId}")
////    public String updateRoom(@PathVariable int roomId, @RequestBody RoomsModel room) {
////        return roomsService.updateRoom(roomId, room);
////    }
////
////    // Delete room by ID
////    @DeleteMapping("/{roomId}")
////    public String deleteRoom(@PathVariable int roomId) {
////        return roomsService.deleteRoom(roomId);
////    }
////}
//
//package com.example.demo.controller;
//
//import com.example.demo.model.RoomsModel;
//import com.example.demo.services.RoomsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.MediaTypeFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Map;
//
//@RestController
// // Base path for all room-related endpoints
//@CrossOrigin
//public class RoomsController {
//
//    @Autowired
//    private RoomsService roomsService;
//
//     
//    @GetMapping("/rooms")
//    public ResponseEntity<List<RoomsModel>> getAllRooms() {
//        List<RoomsModel> rooms = roomsService.getAllRooms();
//        return ResponseEntity.ok(rooms);
//    }
//    @GetMapping("/hotel/{hotelId}/rooms")
//    public List<RoomsModel> getRoomsByHotelId(@PathVariable int hotelId) {
//        return roomsService.getRoomsByHotelId(hotelId);
//    }
//
//    // GET /rooms/{roomId} - Get room by ID
//    @GetMapping("rooms/{roomId}")
//    public ResponseEntity<RoomsModel> getRoomById(@PathVariable int roomId) {
//        RoomsModel room = roomsService.getRoomById(roomId);
//        if (room != null) {
//            return ResponseEntity.ok(room);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
// // POST /rooms/{hotelId} - Add a room to the hotel
//    @PostMapping("/hotel/{hotelId}/addrooms")
//    public ResponseEntity<String> addRoomToHotel(@PathVariable int hotelId, @RequestBody RoomsModel room) {
//        room.setHotel_id(hotelId);  // Set the hotel_id in the room object
//        String message = roomsService.addRoom(room);
//        return ResponseEntity.ok(message);
//    }
//
//    // POST /rooms - Add a new room
//    @PostMapping
//    public ResponseEntity<String> addRoom(@RequestBody RoomsModel room) {
//        String message = roomsService.addRoom(room);
//        return ResponseEntity.ok(message);
//    }
//
//    // PUT /rooms/{roomId}/status - Update room availability status
//    @PutMapping("/{roomId}/status")
//    public ResponseEntity<String> updateAvailability(@PathVariable int roomId, @RequestBody Map<String, String> requestBody) {
//        String newStatus = requestBody.get("availability_status");
//        boolean result = roomsService.updateRoomAvailability(roomId, newStatus);
//
//        if (result) {
//            return ResponseEntity.ok("Room status updated successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Room not found or update failed");
//        }
//    }
//
//    // PUT /rooms/{roomId} - Update room by ID
//    @PutMapping("/{roomId}")
//    public ResponseEntity<String> updateRoom(@PathVariable int roomId, @RequestBody RoomsModel room) {
//        String message = roomsService.updateRoom(roomId, room);
//        return ResponseEntity.ok(message);
//    }
//
//    // DELETE /rooms/{roomId} - Delete room by ID
//    @DeleteMapping("/{roomId}")
//    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
//        String message = roomsService.deleteRoom(roomId);
//        return ResponseEntity.ok(message);
//    }
//    
//    @PostMapping("/roomimages/uploadImage")
//	public String uploadImage(@RequestParam("file") MultipartFile file) {
//		try {
//			// Set the uploads folder path
//			String uploadDir = "D:/HotelBooking/HotelBooking/HotelBookingApplication/uploads/roomimages";
//		    		
//			// ✅ Automatically create the folder if missing
//	        Path uploadPath = Paths.get(uploadDir);
//	        if (!Files.exists(uploadPath)) {
//	            Files.createDirectories(uploadPath);
//	        }
//
//
//			// Save file with original filename
//			Path filePath = uploadPath.resolve(file.getOriginalFilename());
//			file.transferTo(filePath.toFile());
//
//			// Return the URL/path to access the image
//			return "http://localhost:8080/uploads/roomimages/" + file.getOriginalFilename();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Failed to upload image: " + e.getMessage();
//		}
//	}
//	
//	@GetMapping("/uploads/roomimages/{filename:.+}")
//    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//        try {
//            Path filePath = Paths.get("uploads/roomimages").resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (!resource.exists() || !resource.isReadable()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            return ResponseEntity.ok()
//                    .contentType(MediaTypeFactory.getMediaType(resource)
//                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
//                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
//

package com.example.demo.controller;

import com.example.demo.model.RoomsModel;
import com.example.demo.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin

public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    // Get all rooms
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomsModel>> getAllRooms() {
        List<RoomsModel> rooms = roomsService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    // Get rooms by hotel ID
    @GetMapping("/hotel/{hotelId}/rooms")
    public ResponseEntity<List<RoomsModel>> getRoomsByHotelId(@PathVariable int hotelId) {
        List<RoomsModel> rooms = roomsService.getRoomsByHotelId(hotelId);
        return rooms.isEmpty() ?
            ResponseEntity.noContent().build() :
            ResponseEntity.ok(rooms);
    }
    

    // Get room by ID
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomsModel> getRoomById(@PathVariable int roomId) {
        RoomsModel room = roomsService.getRoomById(roomId);
        return room != null ?
            ResponseEntity.ok(room) :
            ResponseEntity.notFound().build();
    }

    // Add a new room (with amenities)
    @PostMapping("/hotel/{hotelId}/addrooms")
    public ResponseEntity<String> addRoomToHotel(@PathVariable int hotelId, @RequestBody RoomsModel room) {
        room.setHotel_id(hotelId);  // Set hotel_id from path
//        System.out.println("Room object: " + room);
//        System.out.println("Amenity IDs: " + room.getAmenityIds());
        boolean isAdded = roomsService.addRoom(room);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Room added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add room");
        }
    }

    // Update room availability status
    @PutMapping("/{roomId}/status")
    public ResponseEntity<String> updateRoomAvailability(@PathVariable int roomId, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("availability_status");
        boolean result = roomsService.updateRoomAvailability(roomId, newStatus);
        if (result) {
            return ResponseEntity.ok("Room availability status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update room status");
        }
    }

    // Update room details (with amenities)
    @PutMapping("/updateRoomById/{roomId}")
    public ResponseEntity<String> updateRoom(@PathVariable int roomId, @RequestBody RoomsModel room) {
        boolean isUpdated = roomsService.updateRoom(room);
        if (isUpdated) {
            return ResponseEntity.ok("Room updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update room");
        }
    }

    // Delete room by ID
    @DeleteMapping("/deleteRoomById/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        boolean isDeleted = roomsService.deleteRoomById(roomId);
        if (isDeleted) {
            return ResponseEntity.ok("Room deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
    }

    // Upload room image
    @PostMapping("/roomimages/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "D:/HotelBooking/HotelBooking/HotelBookingApplication/uploads/roomimages";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());

            return ResponseEntity.ok("http://localhost:8080/uploads/roomimages/" + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    // Retrieve room image
    @GetMapping("/uploads/roomimages/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/roomimages").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaTypeFactory.getMediaType(resource)
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
