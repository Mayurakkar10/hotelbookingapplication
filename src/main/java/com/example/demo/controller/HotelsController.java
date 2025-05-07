package com.example.demo.controller;

import com.example.demo.model.HotelsModel;
import com.example.demo.repository.HotelsRepository;
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

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HotelsController {

	@Autowired
	private HotelsRepository hotelsRepository;

	@PostMapping("/addHotels")
	public String addHotel(@RequestBody HotelsModel hotel) {
		boolean added = hotelsRepository.addHotel(hotel);
		return added ? "Hotel added successfully" : "Failed to add hotel";
	}

	@PostMapping("/addHotels/{ownerId}")
	public String addHotel(@PathVariable int ownerId, @RequestBody HotelsModel hotel) {
		hotel.setOwner_id(ownerId);
		boolean added = hotelsRepository.addHotel(hotel);
		return added ? "Hotel added successfully" : "Failed to add hotel";
	}

	@GetMapping("/getAllHotels")
	public List<HotelsModel> getAllHotels() {
		return hotelsRepository.getAllHotels();
	}



	@PutMapping("/updateHotelById{hotelId}")
	public String updateHotel(@PathVariable int hotelId, @RequestBody HotelsModel hotel) {
		hotel.setHotel_id(hotelId);
		boolean updated = hotelsRepository.updateHotel(hotel);

		if (updated) {
			return "Hotel Updated Successfully...";
		} else {
			return null;
		}
	}

	@GetMapping("/hotels/owner/{ownerId}")
	public List<HotelsModel> getHotelsByOwner(@PathVariable int ownerId) {
		return hotelsRepository.getHotelsByOwnerId(ownerId);
	}
	
	@GetMapping("hotel/{hotelId}")
    public ResponseEntity<?> getHotelById(@PathVariable int hotelId) {
        HotelsModel hotel = hotelsRepository.getHotelById(hotelId);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        } else {
            return ResponseEntity.status(404).body("Hotel not found");
        }
    }

	@DeleteMapping("deleteHotelById/{hotelId}")
	public String deleteHotel(@PathVariable int hotelId) {
		boolean deleted = hotelsRepository.deleteHotelById(hotelId);
		return deleted ? "Hotel deleted successfully" : "Failed to delete hotel";
	}

	@PostMapping("/hotelimage/uploadImage")
	public String uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			// Set the uploads folder path
			String uploadDir = "D:/HotelBooking/HotelBooking/HotelBookingApplication/uploads/hotelimages";
		    		
			// ✅ Automatically create the folder if missing
	        Path uploadPath = Paths.get(uploadDir);
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }


			// Save file with original filename
			Path filePath = uploadPath.resolve(file.getOriginalFilename());
			file.transferTo(filePath.toFile());

			// Return the URL/path to access the image
			return "http://localhost:8080/uploads/hotelimages/" + file.getOriginalFilename();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to upload image: " + e.getMessage();
		}
	}
	
	@GetMapping("/uploads/hotelimages/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/hotelimages").resolve(filename).normalize();
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
