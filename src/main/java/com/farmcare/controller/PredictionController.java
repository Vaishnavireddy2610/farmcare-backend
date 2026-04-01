@PostMapping
public String predict(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
        return "No file uploaded";
    }

    // TEMP FAKE LOGIC
    return "Predicted Disease: Wheat Rust";
}