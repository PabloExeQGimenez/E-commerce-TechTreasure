package com.mindhub.techTreasure;

import com.mindhub.techTreasure.models.*;
import com.mindhub.techTreasure.services.*;
import com.mindhub.techTreasure.utils.ProductUtils;
import com.mindhub.techTreasure.utils.PurchaseOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.mindhub.techTreasure.utils.ProductUtils.generateSKU;
import static com.mindhub.techTreasure.utils.PurchaseOrderUtils.generateNumberOrder;
//import static com.mindhub.techTreasure.utils.PurchaseOUtils;


import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class TechTreasureApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TechTreasureApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(CustomerService customerService, ReviewService reviewService, ProductService productService, CategoryService categoryService, PurchaseOrderService purchaseOrderService,FavoriteService favoriteService, PasswordEncoder passwordEncoder) {
        return args -> {

            List<String> addresses = List.of("Carer 14a #22-66");

            List<String> addressesAdmin = List.of("Carer 14a #22-66");
            List<String> telephonesAdmin = List.of("+56 9 5354 4314");
            List<String> telephones = List.of("+57 3132087910");

            Customer customerAlejandra = new Customer("Alejandra", "Ardila", "Alardilap@gmail.com", addresses, telephones, passwordEncoder.encode("ale123"));
            Customer administrador = new Customer("Administrador", "AdminTech", "TechTreasure@admin.com", addressesAdmin, telephonesAdmin, passwordEncoder.encode("admin"));
            Customer customerPablo = new Customer("Pablo", "Gimenez", "pablo@gmail.com", addresses, telephones, passwordEncoder.encode("pablo"));

            customerService.save(customerPablo);
            customerService.save(customerAlejandra);
            customerService.save(administrador);


            PurchaseOrder purchaseOrder = new PurchaseOrder(generateNumberOrder(purchaseOrderService), LocalDateTime.now(), OrderStatus.PENDING, 123.55, 89745.4,"carera 12#23-56");
            PurchaseOrder purchaseOrder1 = new PurchaseOrder(generateNumberOrder(purchaseOrderService), LocalDateTime.now(), OrderStatus.PENDING, 123.55, 89745.4,"carera 12#23-56");
            PurchaseOrder purchaseOrder2 = new PurchaseOrder(generateNumberOrder(purchaseOrderService), LocalDateTime.now(), OrderStatus.PENDING, 123.55, 89745.4,"carera 12#23-56");

            purchaseOrderService.savePurchaseOrder(purchaseOrder);
            purchaseOrderService.savePurchaseOrder(purchaseOrder1);
            purchaseOrderService.savePurchaseOrder(purchaseOrder2);


            Category notebooks = new Category(CategoryName.NOTEBOOKS, "Portable computing devices with integrated components, including a display, keyboard, and trackpad, designed for on-the-go use.", "3532", true);
            categoryService.saveCategory(notebooks);

            Category monitors = new Category(CategoryName.MONITORS, "Output devices that display visual information from a computer, providing a larger screen for better visibility and user interaction.", "2756", true);
            categoryService.saveCategory(monitors);

            Category videoCards = new Category(CategoryName.VIDEO_CARD, "Hardware component responsible for rendering graphics and images, enhancing the visual experience on a computer or gaming system.", "3472", true);
            categoryService.saveCategory(videoCards);

            Category powerSupply = new Category(CategoryName.POWER_SUPPLY, "Device that provides electrical power to a computer by converting electrical energy from an outlet into a form usable by the computer's components.", "5890", true);
            categoryService.saveCategory(powerSupply);

            Category motherBoards = new Category(CategoryName.MOTHERBOARDS, "Main circuit board containing connectors for other components, facilitating communication between various hardware components in a computer.", "6545", true);
            categoryService.saveCategory(motherBoards);

            Category processors = new Category(CategoryName.PROCESSORS, "Central processing unit (CPU) responsible for executing instructions and performing calculations, serving as the brain of the computer.", "2445", true);
            categoryService.saveCategory(processors);

            Category peripherals = new Category(CategoryName.PERIPHERALS, "Additional devices connected to a computer to provide input, output, or other functionalities, such as printers, scanners, and external drives.", "0985", true);
            categoryService.saveCategory(peripherals);

            Category ram = new Category(CategoryName.RAM, "Random Access Memory (RAM) used for temporary storage of data that the CPU is currently working on, allowing for quick access and retrieval.", "7956", true);
            categoryService.saveCategory(ram);

            Category disks = new Category(CategoryName.DISKS, "Description: Storage devices, including hard disk drives (HDDs) and solid-state drives (SSDs), used for long-term data storage on a computer.", "0985", true);
            categoryService.saveCategory(disks);

            Category computerTower = new Category(CategoryName.COMPUTER_TOWER, "Enclosure that houses the main components of a desktop computer, including the motherboard, CPU, memory, and storage devices.", "6747", true);
            categoryService.saveCategory(computerTower);

            Category desktop = new Category(CategoryName.DESKTOP, " A personal computer designed to be used at a fixed location, typically consisting of a computer tower, monitor, keyboard, and mouse.", "5667", true);
            categoryService.saveCategory(desktop);

            //PRODUCTS NOTEBOOKS YA MARCA

//            String generatedSKU = ProductUtils.generateSKU(productService);

            Product notebook1 = new Product(generateSKU(productService), "Notebook Aiwa Cloudbook CA141-C Intel N4020 4Gb 128Gb 14 W11 C/Sheat", 1484.99, 23, ProductBrand.AIWA, "Elevate your computing experience with the AIWA Cloudbook CA141-C. This sleek 14-inch notebook packs a punch with its Intel N4020 processor, 4GB RAM, and a generous 128GB storage capacity. Running on Windows 11, it ensures a user-friendly interface for seamless multitasking and vibrant visuals. The package is complete with a stylish protective case, making the AIWA Cloudbook CA141-C the perfect blend of performance, portability, and security. Whether you're a student, professional, or tech enthusiast, this notebook caters to your every need, offering a sleek design and powerful capabilities in one dynamic package.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/wcmwzwiyufkjwmu8e93y", true);
            notebook1.addCategory(notebooks);
            productService.saveProduct(notebook1);

            Product notebook2 = new Product(generateSKU(productService), "Notebook Asus X515EA I3 1115G4 4Gb SSD 256Gb 15.6 FHD Free", 1616.99, 34, ProductBrand.ASUS, "Elevate your productivity with the ASUS X515EA. Boasting an Intel i3 1115G4 processor, 4GB RAM, and a swift 256GB SSD, this 15.6-inch notebook delivers a seamless computing experience. The vibrant Full HD display enhances your visuals, providing clarity and detail. Experience the freedom of a sleek and portable design, all bundled with this notebook for an unmatched performance-to-value ratio. Unleash your potential with ASUS X515EA - where power meets portability, and efficiency meets elegance.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/dcjzcarkfckcbfwrtxzf", true);
            notebook2.addCategory(notebooks);
            productService.saveProduct(notebook2);

            Product notebook3 = new Product(generateSKU(productService), "Notebook Noblex Celeron N4020 4Gb 128Gb 14.1 HD W11", 989.99, 21, ProductBrand.NOBLEX, "Experience the perfect blend of efficiency and affordability with the Noblex Notebook featuring an Intel Celeron N4020 processor, 4GB RAM, and a practical 128GB storage capacity. The 14.1-inch HD display brings your content to life, whether you're working or streaming. Running on Windows 11, this notebook ensures a user-friendly interface, making every task a breeze. Elevate your computing experience with the Noblex Celeron N4020 - where performance meets value in a sleek and portable design.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/zpseqot3bsic5zsvqh6u", true);
            notebook3.addCategory(notebooks);
            productService.saveProduct(notebook3);

            Product notebook4 = new Product(generateSKU(productService), "Notebook Noblex Core I3 1115g4 8Gb 256Gb 14.1 FHD W11", 1484.99, 30, ProductBrand.NOBLEX, "Unleash exceptional performance with the Lenovo IdeaPad 1 powered by the Ryzen 5 3500U processor. With 8GB of RAM and a swift 256GB SSD, this 14-inch notebook is engineered for seamless multitasking and fast data access. The package includes a stylish protective case for added convenience. Ideal for work, study, or entertainment, the Lenovo IdeaPad 1 combines power and portability in a sleek design. Elevate your computing experience with this Ryzen-driven powerhouse that comes with the added bonus of a protective case at no extra cost.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/yzj3dsgvk5gqydhusnsq", true);
            notebook4.addCategory(notebooks);
            productService.saveProduct(notebook4);

            Product notebook5 = new Product(generateSKU(productService), "Notebook Lenovo Ideapad 1 RyZEN 5 3500u 8Gb SSD 256Gb 14 Free", 1649.97, 18, ProductBrand.LENOVO, "Unleash exceptional performance with the Lenovo IdeaPad 1 powered by the Ryzen 5 3500U processor. With 8GB of RAM and a swift 256GB SSD, this 14-inch notebook is engineered for seamless multitasking and fast data access. The package includes a stylish protective case for added convenience. Ideal for work, study, or entertainment, the Lenovo IdeaPad 1 combines power and portability in a sleek design. Elevate your computing experience with this Ryzen-driven powerhouse that comes with the added bonus of a protective case at no extra cost.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/yzj3dsgvk5gqydhusnsq", true);
            notebook5.addCategory(notebooks);
            productService.saveProduct(notebook5);

            Product notebook6 = new Product(generateSKU(productService), "Notebook Bangho Max L5 I7 1195G7 SSD 480Gb 8Gb 15.6 FHD Free", 2144.97, 21, ProductBrand.BANGHO, "Experience unparalleled performance with the Bangho Max L5 featuring an Intel i7-1195G7 processor. Boasting 8GB of RAM and a rapid 480GB SSD, this 15.6-inch FHD notebook delivers a seamless and responsive computing experience. The package comes with a complimentary accessory, providing added value. Whether you're working on complex tasks or enjoying multimedia content, the Bangho Max L5 strikes the perfect balance of power and style in a generously-sized display. Elevate your computing journey with this i7-driven powerhouse, complete with a valuable free accessory.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/gwqfzwymncoakn3mieuz", true);
            notebook6.addCategory(notebooks);
            productService.saveProduct(notebook6);


            //PRODUCTS RAM YA MARCA


            Product ram1 = new Product(generateSKU(productService), "Memoria Ram Kingston Fury Renegade DDR4 8Gb 3600MHZ RGB", 154.99, 27, ProductBrand.KINGSTON_FURY, "Elevate your system's performance and aesthetics with the Kingston Fury Renegade DDR4 RAM. Boasting 8GB capacity and a swift 3600MHz speed, this RAM module delivers blazing-fast data transfer for seamless multitasking and responsive applications. The RGB lighting adds a touch of flair to your setup, providing a visually stunning experience. Upgrade your system with Kingston Fury Renegade DDR4 8GB RAM and immerse yourself in high-speed, vibrant computing.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/mvropjw5uzh3tiwu1vqv", true);
            ram1.addCategory(ram);
            productService.saveProduct(ram1);

            Product ram2 = new Product(generateSKU(productService), "Memoria Ram Kingston DDR4 16Gb 3200MHZ", 204.59, 43, ProductBrand.KINGSTON, "Enhance your system's performance with the Kingston DDR4 RAM. With a substantial 16GB capacity and a clock speed of 3200MHz, this RAM module delivers reliable and efficient multitasking capabilities. Ideal for various computing tasks, from content creation to gaming, it ensures a smooth and responsive experience. Upgrade your system's memory with Kingston DDR4 16GB RAM, and enjoy improved speed and efficiency for your daily computing needs.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/ek5icarqhjnooersyxsm", true);
            ram2.addCategory(ram);
            productService.saveProduct(ram2);

            Product ram3 = new Product(generateSKU(productService), "Ram DDR5 Adata XPG Lancer 32Gb 5600MHZ", 659.97, 29, ProductBrand.ADATA_XPG, "Unleash the power of your system with the ADATA XPG Lancer DDR5 RAM. Featuring an impressive 32GB capacity and a blazing-fast 5600MHz speed, this RAM module delivers exceptional performance for demanding tasks such as gaming, content creation, and more. The DDR5 technology ensures enhanced data transfer rates and efficiency. Elevate your computing experience with the ADATA XPG Lancer DDR5 32GB RAM, and enjoy unparalleled speed and responsiveness for a seamless and immersive computing journey.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/vixrdacawblxjc5pdhay", true);
            ram3.addCategory(ram);
            productService.saveProduct(ram3);

            Product ram4 = new Product(generateSKU(productService), "Ram Corsair Vengance DDR4 16Gb 3600MHZ Lpx 2x8 Black", 240.87, 54, ProductBrand.CORSAIR, "Upgrade your system's performance with the Corsair Vengeance LPX DDR4 RAM. This kit includes two 8GB modules, totaling 16GB, and operates at a speedy 3600MHz, ensuring smooth multitasking and efficient data processing. The low-profile design of the LPX series allows for compatibility with a variety of systems, and the sleek black finish adds a touch of sophistication to your setup. Elevate your gaming or productivity with the Corsair Vengeance DDR4 16GB RAM, delivering reliable and high-speed performance in a stylish package.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/fzynimru4iwtbixuv7ry", true);
            ram4.addCategory(ram);
            productService.saveProduct(ram4);

            Product ram5 = new Product(generateSKU(productService), "Ram Pny DDR4 8Gb 3200MHZ XLR8 RGB Gaming White", 108.89, 43, ProductBrand.PNY, "Elevate your gaming experience with the PNY XLR8 RGB Gaming DDR4 RAM. Boasting 8GB capacity and a rapid 3200MHz speed, this module delivers high-performance computing with a focus on aesthetics. The vibrant RGB lighting, set against a stylish white design, adds a dynamic touch to your gaming rig. Upgrade your system with PNY DDR4 8GB RAM, and immerse yourself in a combination of speed, reliability, and visually stunning RGB effects for an enhanced gaming setup.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/jdvty0rwnxqdbwvbvjub", true);
            ram5.addCategory(ram);
            productService.saveProduct(ram5);

            Product ram6 = new Product(generateSKU(productService), "Ram DDR4 16Gb 3600MHZ Patriot Viper Steel RGB Cl20 2x8", 260.67, 32, ProductBrand.PATRIOT, "Enhance your system's performance with the Patriot Viper Steel RGB DDR4 RAM. This kit includes two 8GB modules, totaling 16GB, and operates at a speedy 3600MHz with a CL20 latency. The RGB lighting adds a dynamic and vibrant touch to your setup, combining style with high-speed data transfer. Elevate your gaming or productivity with the Patriot Viper Steel RGB DDR4 16GB RAM, delivering reliable and efficient performance in a visually striking package.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/a4cdubheklsv5ptarqf5", true);
            ram6.addCategory(ram);
            productService.saveProduct(ram6);


            //PRODUCT PROCESSORS YA MARCA

            Product processors1 = new Product(generateSKU(productService), "Intel Celeron G5905 Tray Oem Bulk 3.50GHZ 4Mb 1200 Microprocessor", 250.77, 26, ProductBrand.INTEL, "Experience reliable computing power with the Intel Celeron G5905 processor. Running at a clock speed of 3.50GHz and equipped with a 4MB cache, this processor offers efficient performance for everyday tasks. The tray OEM bulk packaging ensures cost-effectiveness and flexibility in deployment. Whether you're handling office work or general computing, the Intel Celeron G5905 provides a solid foundation for a responsive and capable system. Upgrade your setup with this processor and enjoy a balance of performance and value.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/jyb18wuu3aamp7qlyzdw", true);
            processors1.addCategory(processors);
            productService.saveProduct(processors1);

            Product processors2 = new Product(generateSKU(productService), "Intel Core I5 12400f Alderlake 4.40GHZ 18Mb LGA1700 Microprocessor", 791.99, 34, ProductBrand.INTEL, "Immerse yourself in powerful computing with the Intel Core i5-12400F processor from the Alder Lake series. Running at an impressive clock speed of 4.40GHz and featuring an 18MB cache, this LGA1700 processor delivers high-speed and responsive performance. Ideal for gaming, content creation, and multitasking, the Intel Core i5-12400F is designed to handle demanding tasks with ease. Upgrade your system with this Alder Lake processor and experience a new level of efficiency and speed in your computing endeavors.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/cxec25w3zadfyps4qr5v", true);
            processors2.addCategory(processors);
            productService.saveProduct(processors2);

            Product processors3 = new Product(generateSKU(productService), "Amd RyZEN 5600g 4.4 GHZ AM4 Microprocessor", 650.07, 28, ProductBrand.AMD, "Experience exceptional performance with the AMD Ryzen 5 5600G processor. With a clock speed of 4.4 GHz and AM4 socket compatibility, this processor is designed for impressive speed and responsiveness. Whether you're gaming, content creation, or handling multitasking, the Ryzen 5 5600G delivers powerful computing capabilities. Upgrade your system with this AMD processor and enjoy a seamless computing experience with advanced features and reliable performance on the AM4 platform.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/znvqhu60ydu9daztiani", true);
            processors3.addCategory(processors);
            productService.saveProduct(processors3);

            Product processors4 = new Product(generateSKU(productService), "Offer CPU Microprocessor Intel Core I9 10900k Cometlake 5.3GHZ 20Mb LGA1200", 1979.97, 34, ProductBrand.INTEL, "Unlock extraordinary performance with the Intel Core i9-10900K processor from the Comet Lake series, now available in the outlet. Running at a remarkable 5.3GHz and featuring a substantial 20MB cache, this LGA1200 processor is designed for high-speed and efficient computing. Ideal for gaming, content creation, and demanding tasks, the Core i9-10900K delivers unparalleled performance. Take advantage of this outlet opportunity to upgrade your system and experience the power of Intel's flagship processor at a discounted price. Elevate your computing capabilities with the Core i9-10900K for an exceptional performance boost.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/nhbzvwr22mbfg2bf8ld0", true);
            processors4.addCategory(processors);
            productService.saveProduct(processors4);

            Product processors5 = new Product(generateSKU(productService), "CPU Amd RyZEN 9 7900 X3d Raphael ZEN4 AM5 Microprocessor", 2705.97, 45, ProductBrand.AMD, "The AMD Ryzen 9 7900X3D is the ultimate gaming processor. This Zen 4 CPU offers a whopping 12 physical cores and 24 logical cores, with frequencies ranging from 4.4 GHz to 5.6 GHz and an impressive 140 MB of V-Cache, allowing you to achieve new levels of performance in gaming. This gaming-oriented processor, based on the revolutionary AMD Zen 4 architecture, is capable of handling everything from cutting-edge games to simultaneous streaming and intensive multitasking.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/wvqesh9ofnxhsjgrrp53", true);
            processors5.addCategory(processors);
            productService.saveProduct(processors5);

            Product processors6 = new Product(generateSKU(productService), "Intel Core I3 10105f 4.4GHZ 6Mb LGA1200 Microprocessor", 316.77, 34, ProductBrand.INTEL, "Unlock efficient computing power with the Intel Core i3-10105F processor. Boasting a base clock speed of 4.4 GHz and a 6MB cache, this LGA1200 processor is designed to deliver reliable and responsive performance for your everyday computing needs. The \"F\" designation signifies the absence of integrated graphics, making it an ideal choice for users who plan to use a discrete graphics card. Upgrade your system with the Intel Core i3-10105F and experience a balance of speed and efficiency in a compact and capable package.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/rfkq5lwzxvrbsq2wgbjy", true);
            processors6.addCategory(processors);
            productService.saveProduct(processors6);


            //PRODUCT VIDEO CARD YA MARCA

            Product videoCard1 = new Product(generateSKU(productService), "Powercolor RX550 Low Profile 4Gb GDDR5 Video Card", 626.99, 34, ProductBrand.POWERCOLOR, "Elevate your graphics performance with the PowerColor RX550 Low Profile graphics card. With 4GB of GDDR5 memory, this compact yet powerful card is designed for efficiency and versatility. The low-profile design makes it suitable for space-constrained systems without compromising on graphics capabilities. Whether you're into casual gaming, content creation, or multimedia tasks, the PowerColor RX550 offers a balance of performance and form factor. Upgrade your graphics experience and enjoy smooth visuals with this reliable and space-saving solution.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/v4rrmxinuym9shsycu5y", true);
            videoCard1.addCategory(videoCards);
            productService.saveProduct(videoCard1);

            Product videoCard2 = new Product(generateSKU(productService), "Asus Rog Strix Nvidia Geforce RTX 4080 Oc 16Gb GDDR6x Video Card", 7589.99, 23, ProductBrand.ASUS, "The ASUS TUF Gaming GeForce RTX™ 4080 has been redesigned to accommodate the new NVIDIA Ada Lovelace architecture. With up to double the performance of the previous generation and an entirely new design, the ASUS TUF Gaming GeForce RTX™ 4080 will deliver your next unparalleled gaming experience.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/bp9b9ccbl2c6x19yb8fq", true);
            videoCard2.addCategory(videoCards);
            productService.saveProduct(videoCard2);

            Product videoCard3 = new Product(generateSKU(productService), "Asus Nvidia Gt 1030 DDR5 2Gb Video Card", 386.09, 25, ProductBrand.ASUS, "Unlock smooth and efficient graphics performance with the ASUS NVIDIA GT 1030 graphics card. Equipped with 2GB of DDR5 memory, this card is designed for reliable and responsive visuals. Whether you're tackling casual gaming, content streaming, or everyday computing tasks, the GT 1030 offers a balance of performance and affordability. The DDR5 memory ensures faster data transfer rates, enhancing your overall graphics experience. Upgrade your system with the ASUS NVIDIA GT 1030 2GB and enjoy a reliable solution for your graphics needs.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/d0ebvwmskfvtueuxgacv", true);
            videoCard3.addCategory(videoCards);
            productService.saveProduct(videoCard3);


            Product videoCard4 = new Product(generateSKU(productService), "Powercolor Amd Radeon RX 6700 10Gb GDDR6 2235 Oem Video Card", 1319.99, 34, ProductBrand.POWERCOLOR, "Experience exceptional graphics performance with the PowerColor AMD Radeon RX 6700 graphics card. Boasting 10GB of GDDR6 memory, this card is engineered for high-speed and immersive gaming experiences. With a base clock speed of 2235 MHz, it delivers smooth and responsive visuals for demanding games and graphics-intensive applications. Upgrade your system with the PowerColor RX 6700 and immerse yourself in the world of cutting-edge graphics, providing the power and capability needed for an enhanced gaming and multimedia experience.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/pxmwcqe8r83fsmdbcwvf", true);
            videoCard4.addCategory(videoCards);
            productService.saveProduct(videoCard4);

            Product videoCard5 = new Product(generateSKU(productService), "Nvidia Geforce RTX 4090 XLR8 Gaming Verto Epic X RGB 24Gb GDDR6x Video Card", 7919.99, 34, ProductBrand.NVIDIA_GEFORCE, "The NVIDIA® GeForce RTX® 4090 is the ultimate GeForce GPU. It delivers a significant leap in performance, efficiency, and AI-driven graphics. Experience ultra-high-performance gaming, incredibly detailed virtual worlds with ray tracing, unparalleled productivity, and new ways to create. It is powered by the NVIDIA Ada Lovelace architecture and comes with 24GB of G6X memory to provide the best experience for gamers and creators.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/bh1egcbttwfidavqss7f", true);
            videoCard5.addCategory(videoCards);
            productService.saveProduct(videoCard5);

            Product videoCard6 = new Product(generateSKU(productService), "Gigabyte Nvidia Geforce RTX 4080 Aero Oc 16Gb GDDR6x Video Card", 5939.99, 43, ProductBrand.GIGABYTE, "The NVIDIA® GeForce RTX® 40 Series GPUs are more than just fast for gamers and creators. They feature the technology of the ultra-efficient NVIDIA Ada Lovelace architecture, providing a spectacular leap in both performance and AI-driven graphics. Enjoy realistic virtual worlds with ray tracing, ultra-high FPS gaming, and the lowest latency. Discover new and revolutionary ways to create content and unprecedented workflow acceleration.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/h9tbjbwhycf9a7czwjec", true);
            videoCard6.addCategory(videoCards);
            productService.saveProduct(videoCard6);


            //PRODUCT MONITORS YA MARCA

            Product monitors1 = new Product(generateSKU(productService), "Monitor 19 Philips 193v5lhsb2/55 HDMI HD 60HZ", 219.9, 24, ProductBrand.PHILIPS, "Immerse yourself in clear visuals with the Philips 193V5LHSB2/55 monitor. Featuring a 19-inch display, this monitor offers a compact and efficient viewing experience. With HDMI connectivity, you can easily connect your devices for high-definition content. Enjoy crisp and vibrant visuals in HD resolution, while the 60Hz refresh rate ensures smooth and responsive performance. Whether you're working, browsing, or watching your favorite content, the Philips 193V5LHSB2/55 provides a reliable and budget-friendly display solution with the convenience of HDMI connectivity.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/frmaheqd1ce2v4hp1lkq", true);
            monitors1.addCategory(monitors);
            productService.saveProduct(monitors1);

            Product monitors2 = new Product(generateSKU(productService), "Monitor 27 Aoc G2790vx 144HZ Freesync", 709.47, 24, ProductBrand.AOC, "Immerse yourself in the ultimate gaming experience with the AOC G2790VX monitor. Boasting a spacious 27-inch display, this monitor delivers stunning visuals and ample screen real estate. With a high refresh rate of 144Hz and support for FreeSync technology, it ensures buttery-smooth gameplay, reducing screen tearing and stuttering. Whether you're gaming, streaming, or working on creative projects, the AOC G2790VX offers a responsive and dynamic display. Dive into fast-paced action with the combination of a large screen, high refresh rate, and adaptive sync technology. Elevate your gaming setup with the AOC G2790VX for an immersive and fluid visual experience.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/kxfvvkyqnyrwkfb0xnb7", true);
            monitors2.addCategory(monitors);
            productService.saveProduct(monitors2);

            Product monitors3 = new Product(generateSKU(productService), "Monitor 25 Samsung Led Oddyssey G4 240HZ", 791.99, 39, ProductBrand.SAMSUNG, "Experience gaming at its finest with the Samsung Odyssey G4, a 25-inch LED monitor that takes your visual experience to the next level. Boasting a blazing-fast 240Hz refresh rate, this monitor delivers ultra-smooth and responsive gameplay, perfect for fast-paced action and competitive gaming. The Odyssey G4 not only offers speed but also features Samsung's signature attention to detail with vivid colors and sharp visuals. Whether you're deep into gaming or engaging in multimedia tasks, the high refresh rate ensures a seamless and immersive experience. Upgrade your gaming setup with the Samsung Odyssey G4 25-inch monitor for a thrilling and responsive visual journey.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/ece4znusem5mz4yfsdih", true);
            monitors3.addCategory(monitors);
            productService.saveProduct(monitors3);

            Product monitors4 = new Product(generateSKU(productService), "Monitor 27 Gigabyte M27f A FHD Kvm HDr 165HZ", 791.99, 37, ProductBrand.GIGABYTE, "Immerse yourself in stunning visuals with the Gigabyte M27F, a 27-inch monitor designed for a premium viewing experience. Featuring a Full HD display, this monitor delivers crisp and vibrant images for both work and entertainment. With a high refresh rate of 165Hz, it ensures smooth and responsive performance, ideal for gaming and fast-paced content. The M27F also comes equipped with KVM (Keyboard, Video, and Mouse) functionality, providing convenient multi-device control with a single set of peripherals. HDR support enhances contrast and color accuracy, elevating your visual experience further. Upgrade your display setup with the Gigabyte M27F for a combination of high refresh rates, KVM functionality, and HDR support, making it a versatile and immersive choice for various applications.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/vfrjwjfudbs3x0tvjakv", true);
            monitors4.addCategory(monitors);
            productService.saveProduct(monitors4);

            Product monitors5 = new Product(generateSKU(productService), "Monitor 24 Led Samsung T350 FHD Ips 5ms 75HZ", 329.99, 35, ProductBrand.SAMSUNG, "Elevate your visual experience with the Samsung T350, a 24-inch LED monitor designed for clear and vibrant display. With a Full HD resolution and IPS panel technology, this monitor delivers sharp and vivid visuals with wide viewing angles. The T350 boasts a 5ms response time, ensuring smooth motion for videos and games, while the 75Hz refresh rate contributes to a responsive and immersive viewing experience. Whether you're working, streaming, or gaming, the Samsung T350 provides a sleek and reliable display solution with its combination of FHD clarity and IPS technology. Upgrade your desktop setup with the T350 for a versatile and visually pleasing monitor.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/ldurggrwuvbr759k76ss", true);
            monitors5.addCategory(monitors);
            productService.saveProduct(monitors5);

            Product monitors6 = new Product(generateSKU(productService), "Monitor 32 Viewsonic Elite Xg320u 150HZ 4K", 4493.29, 34, ProductBrand.VIEWSONIC, "Dive into an unparalleled visual experience with the ViewSonic Elite XG320U, a 32-inch monitor that redefines clarity and performance. Boasting a stunning 4K resolution, this monitor delivers ultra-sharp and detailed visuals for both work and entertainment. The XG320U features a high refresh rate of 150Hz, providing smooth and responsive gameplay, making it an ideal choice for gaming enthusiasts. Whether you're indulging in 4K content, gaming, or handling creative tasks, this monitor offers a perfect blend of resolution and speed. Upgrade your viewing setup with the ViewSonic Elite XG320U for a premium display that combines 4K clarity with a high refresh rate for an immersive visual experience.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/teufdar8h7wn3hxlej4h", true);
            monitors6.addCategory(monitors);
            productService.saveProduct(monitors6);


            //PRODUCT DESKTOP YA MARCA

            Product desktop1 = new Product(generateSKU(productService), "PC Intel I3 12100 8Gb SSD 240Gb + Monitor 22", 1763.42, 34, ProductBrand.INTEL, "Immerse yourself in efficient computing with the Intel Core i3-12100 PC, equipped with 8GB of RAM and a fast 240GB SSD. This setup is complemented by a 22-inch monitor, offering a balanced and responsive computing experience for various tasks.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/rhzfzzduu9t6bf6dlwiu", true);
            desktop1.addCategory(desktop);
            productService.saveProduct(desktop1);

            Product desktop2 = new Product(generateSKU(productService), "PC Amd RyZEN 5 4600g 8Gb SSD 240g", 1328.59, 35, ProductBrand.AMD, "Experience powerful computing with the AMD Ryzen 5 4600G PC, featuring 8GB of RAM and a speedy 240GB SSD. This configuration delivers a responsive and efficient performance for a variety of tasks.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/v57hpiqll9nvvxuchxpn", true);
            desktop2.addCategory(desktop);
            productService.saveProduct(desktop2);

            Product desktop3 = new Product(generateSKU(productService), "PC Intel I5 10400 SSD 240Gb 8Gb", 1690.97, 43, ProductBrand.INTEL, "Dive into powerful computing with the Intel Core i5-10400 PC, featuring a 240GB SSD and 8GB of RAM. This configuration ensures a responsive and efficient performance for a range of computing tasks.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/fuqddqaovcyygwtqlnss", true);
            desktop3.addCategory(desktop);
            productService.saveProduct(desktop3);

            Product desktop4 = new Product(generateSKU(productService), "PC Intel I7 10700 SSD 240Gb 8Gb", 2367.42, 25, ProductBrand.INTEL, "Experience high-performance computing with the Intel Core i7-10700 PC, equipped with a fast 240GB SSD and 8GB of RAM. This configuration ensures a powerful and efficient system for various computing needs.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/jztp5jv5ib5gupunwem1", true);
            desktop4.addCategory(desktop);
            productService.saveProduct(desktop4);

            Product desktop5 = new Product(generateSKU(productService), "PC Amd RyZEN 7 5700g 8Gb SSD 240Gb", 1932.54, 24, ProductBrand.AMD, "Dive into robust computing with the AMD Ryzen 7 5700G PC, featuring 8GB of RAM and a speedy 240GB SSD. This configuration delivers powerful and efficient performance for a variety of computing tasks.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/jsns1zz1yhrp0obqadqw", true);
            desktop5.addCategory(desktop);
            productService.saveProduct(desktop5);


            //PRODUCT POWER SUPPLY
            Product powerSupply1 = new Product(generateSKU(productService), "Aerocool Cylon 500w RGB 80+ Bronze Power Supply", 164.97, 6, ProductBrand.AEROCOOL, "Power up your system with the AeroCool Cylon 500W RGB power supply. Featuring an 80+ Bronze certification, this power supply ensures efficient and reliable performance. The inclusion of RGB lighting adds a vibrant and stylish touch to your system, enhancing its visual appeal. Upgrade your setup with the AeroCool Cylon 500W for a combination of power, efficiency, and customizable RGB aesthetics.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/vmpjfq834tz819sxkymq", true);
            powerSupply1.addCategory(powerSupply);
            productService.saveProduct(powerSupply1);

            Product powerSupply2 = new Product(generateSKU(productService), "Cooler Master 750w V2 80+ Bronze MWE Power Supply", 290.37, 9, ProductBrand.COOLER_MASTER, "Elevate your system's power with the Cooler Master 750W V2 80+ Bronze MWE Power Supply. With its 80+ Bronze certification, this power supply ensures efficiency and reliable performance. Delivering 750 watts of power, it is capable of handling demanding setups. The Cooler Master design emphasizes durability and performance, making it a solid choice for various computing needs. Upgrade your system with the Cooler Master 750W V2 for a reliable and efficient power supply solution.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/g3fnr62ull8lqjguclga", true);
            powerSupply2.addCategory(powerSupply);
            productService.saveProduct(powerSupply2);

            Product powerSupply3 = new Product(generateSKU(productService), "Thermaltake Smart White 700w 80 Plus Power Supply", 244.17, 41, ProductBrand.THERMALTAKE, "Enhance your system's power with the Thermaltake Smart White 700W 80 PLUS Power Supply. This power supply unit delivers 700 watts of reliable power with an 80 PLUS efficiency rating, ensuring optimal performance. The white color adds a sleek and stylish touch to your system, complementing a variety of setups. Upgrade your system with the Thermaltake Smart White 700W for a reliable and efficient power supply that also contributes to the aesthetics of your build.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/djthqnhwroxz8f7znoj5", true);
            powerSupply3.addCategory(powerSupply);
            productService.saveProduct(powerSupply3);

            Product powerSupply4 = new Product(generateSKU(productService), "Redragon 800w 80+ Bronze Power Supply", 296.97, 7, ProductBrand.REDRAGON, "Boost your system's power with the Redragon 800W 80+ Bronze Power Supply. With an 80+ Bronze certification, this power supply ensures efficiency and reliable performance. Delivering 800 watts of power, it is suitable for handling demanding setups, providing stability for your components. Upgrade your system with the Redragon 800W for a reliable and efficient power supply solution, supporting your gaming or professional computing needs.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/vdw64hxuspxjh0xozcge", true);
            powerSupply4.addCategory(powerSupply);
            productService.saveProduct(powerSupply4);

            Product powerSupply5 = new Product(generateSKU(productService), "Azza 750w 80+ Bronze ARGB Power Supply", 290.37, 8, ProductBrand.AZZA, "Upgrade your system with the AZZA 750W 80+ Bronze ARGB Power Supply, combining reliable power delivery with vibrant ARGB lighting. With an 80+ Bronze certification, this power supply ensures efficiency and stable performance. The addition of Addressable RGB lighting adds a dynamic and customizable touch to your system, allowing you to enhance its visual appeal. Elevate your build with the AZZA 750W for a powerful and aesthetically pleasing power supply solution.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/aqxlsmfjlmysuqc1gkdg", true);
            powerSupply5.addCategory(powerSupply);
            productService.saveProduct(powerSupply5);

            Product powerSupply6 = new Product(generateSKU(productService), "Power Supply 500w Generic", 44.77, 8, ProductBrand.VARIOUS, "Consider upgrading your system with a 500W generic power supply. While generic power supplies may not carry a specific brand, they can still provide basic power for moderate setups. Keep in mind that generic power supplies might have varying levels of efficiency and reliability, and they may lack some features found in branded alternatives. Assess your system's power needs and compatibility before opting for a generic power supply, and ensure it meets the requirements of your components for a stable and efficient performance.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/js3vh0ktpx56pieraigc", true);
            powerSupply6.addCategory(powerSupply);
            productService.saveProduct(powerSupply6);


            //PRODUCT MOTHERBOARDS
            Product motherBoard1 = new Product(generateSKU(productService), "Outlet Motheboard Gigabyte H310m M.2 2.0 S1151", 132.00, 3, ProductBrand.GIGABYTE, "It seems there might be a slight typo in your query, and you're referring to an 'OUTLET MOTHERBOARD GIGABYTE H310M M.2 2.0 S1151.' If you mean an 'OUTLET' motherboard, please note that motherboards typically do not have an 'OUTLET' designation. However, if you're referring to a motherboard with an 'LGA 1151' socket, supporting the Intel H310 chipset, and featuring an M.2 slot, here's a revised response: Consider the Gigabyte H310M M.2 2.0 motherboard for your system. Designed for Intel processors with an LGA 1151 socket, this motherboard supports the H310 chipset and features an M.2 slot for fast storage options. Double-check your system requirements and ensure compatibility before making a purchase.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/qyqwpzgp2gg1cmuriuu6", true);
            motherBoard1.addCategory(motherBoards);
            productService.saveProduct(motherBoard1);

            Product motherBoard2 = new Product(generateSKU(productService), "Motherboard Msi Pro Z790-P DDR4 S1700", 907.50, 36, ProductBrand.MSI, "PRO Z790-P is designed with tons of flexible tools and a convenient Wi-Fi solution with DDR5 memory support. The PRO series is tailored for professionals from all walks of life. The lineup boasts impressive performance and high quality while aiming to deliver an amazing user experience. Users who care about productivity and efficiency can certainly rely on the MSI PRO series to assist with multitasking and enhance efficiency.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/qwqkdyvegpkebhyrjl6e", true);
            motherBoard2.addCategory(motherBoards);
            productService.saveProduct(motherBoard2);

            Product motherBoard3 = new Product(generateSKU(productService), "Motherboard Asus Prime Z790-P Wifi LGA 1700", 1072.50, 5, ProductBrand.ASUS, "The ASUS GAMING Z790 WiFi takes all the essentials of the latest 13th generation Intel® Core™ processors and combines them with game-ready features and proven durability. Designed with military-grade components, an enhanced power solution, and a comprehensive cooling system, this motherboard delivers solid and stable performance for marathon gaming sessions. Aesthetically, the TUF GAMING Z790-PLUS WiFi showcases the new TUF Gaming logo and incorporates simple geometric design elements to reflect the reliability and stability that define the TUF Gaming series.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/l6f8junvtcgmbklxjdnv", true);
            motherBoard3.addCategory(motherBoards);
            productService.saveProduct(motherBoard3);

            Product motherBoard4 = new Product(generateSKU(productService), "Motherboard Asus Rog Strix X670e-E Gaming Wifi AM5", 1980.00, 7, ProductBrand.ASUS, "The ASUS ROG STRIX X670E-E GAMING WIFI motherboard is designed for gamers seeking an optimal gaming experience with 18+2 power stages and optimized cooling to maximize the demands of Ryzen 7000 series processors. Alongside the latest Wi-Fi , DDR5 memory, and PCI Express® 5.0 for ultra-fast data transfer, memory, and storage performance, the ROG STRIX X670E-E GAMING WIFI is unmistakably ROG with its futuristic design elements, iridescent ROG logo, monochrome finish, and Aura lighting that will give your build a subtle yet vibrant appearance.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/vtpbvseipu9yc2be4omq", true);
            motherBoard4.addCategory(motherBoards);
            productService.saveProduct(motherBoard4);

            Product motherBoard5 = new Product(generateSKU(productService), "Motherboard Biostar A320mh AM4", 244.20, 4, ProductBrand.BIOSTAR, "The Biostar A320MH is an AM4 motherboard that supports a range of AMD Ryzen processors, providing a reliable foundation for your system. Featuring the A320 chipset, it offers essential features for mainstream computing. The AM4 socket ensures compatibility with a variety of Ryzen CPUs, and the motherboard typically supports DDR4 memory modules for efficient multitasking. While it may not have the advanced features of higher-end chipsets, the A320MH is a cost-effective choice for users seeking stability and functionality for their AMD-based systems. Always check the official product documentation or the Biostar website for the most up-to-date information on specifications and features.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/y5oo2ybglgtoizezwcdm", true);
            motherBoard5.addCategory(motherBoards);
            productService.saveProduct(motherBoard5);

            Product motherBoard6 = new Product(generateSKU(productService), "Motherboard Gigabyte B650m DS3H Box AM5", 775.50, 9, ProductBrand.GIGABYTE, "Compatible with AMD Ryzen 7000 series processors. Supports DDR5: 4 SMD DIMM slots compatible with Intel XMP and AMD EXPO memory modules. Robust Power Design: 6+2+1 direct digital power phase VRM. Comprehensive Thermal Design: Advanced thermal design, M.2 Thermal Guard. Next-Gen Connectivity: PCIe 5.0, NVMe PCIe 4.0 x4 M.2, high-speed USB 3.2 Gen 2x2 Type-C. Fast Networking: 2.5 GbE LAN. Fine-Tuning Features: RGB Fusion 2.0, supports RGB LED and addressable LED strips, Smart Fan 6, BIOS update with Q-Flash Plus without CPU, memory, and graphics processing unit installation. A new era is here. The B650M DS3H is compatible with AM5 processors and offers unparalleled performance. GIGABYTE's B650 motherboards come with enhanced power design, high-grade storage standards, and outstanding connectivity, allowing you to optimize your performance.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/y1rh8xawuhotj2j96yhu", true);
            motherBoard6.addCategory(motherBoards);
            productService.saveProduct(motherBoard6);

            //JONATAN
            //PRODUCT PERIPHERALS


            Product peripheral1 = new Product(generateSKU(productService), "HDC Kb113 USB Keyboard", 25.92, 8, ProductBrand.HDC, "The HDC KB113 USB Keyboard is a reliable input device designed for seamless connectivity with your computer. With a USB interface, it offers easy plug-and-play functionality, allowing you to start typing without the need for additional drivers. The keyboard likely features a standard layout with a full set of keys, including function keys, and is suitable for everyday use, whether for work, gaming, or general computing tasks. Always refer to the product specifications or the manufacturer's documentation for detailed information about features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/t3t61dcw6c6bzq7zee49", true);
            peripheral1.addCategory(peripherals);
            productService.saveProduct(peripheral1);

            Product peripheral2 = new Product(generateSKU(productService), "HypeRX Alloy Origins Core Red Mechanical Keyboard", 329.97, 2, ProductBrand.HYPERX, "The HyperX Alloy Origins Core Red Mechanical Keyboard offers a compact and streamlined design for gamers who prefer a smaller form factor. It features HyperX Red mechanical switches, providing a linear and responsive keypress experience, ideal for gaming. The keyboard is constructed with a durable aluminum top plate for added stability and longevity. It also includes customizable RGB backlighting, allowing users to personalize the keyboard's lighting effects to match their setup.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/l9ylzessyairsvhj4tmz", true);
            peripheral2.addCategory(peripherals);
            productService.saveProduct(peripheral2);

            Product peripheral3 = new Product(generateSKU(productService), "Mouse Game Pro Gm-01 6d Pixart 3325 Dpi", 23.07, 12, ProductBrand.GAME_PRO, "The Mouse Game Pro GM-01 is a 6D gaming mouse designed for gamers seeking precision and responsiveness. It features the Pixart 3325 sensor, offering customizable DPI (dots per inch) settings for enhanced sensitivity. With six buttons, including the standard left and right buttons, a clickable scroll wheel, and additional side buttons, the mouse provides versatile input options for gaming and productivity.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/mzuo5houdli5i0fbngix", true);
            peripheral3.addCategory(peripherals);
            productService.saveProduct(peripheral3);

            Product peripheral4 = new Product(generateSKU(productService), "Wireless Mouse Genius Nx-7000 Black", 52.80, 3, ProductBrand.GENIUS, "The Genius NX-7000 Black is a wireless mouse designed for convenient and comfortable everyday use. Its compact and ergonomic design allows for comfortable handling, while the wireless connectivity provides freedom of movement. With precise optical tracking, plug-and-play functionality, and a classic black color, this mouse offers a reliable and straightforward solution for users seeking simplicity and efficiency in their computing experience.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/sorknljcte14ino5o2zx", true);
            peripheral4.addCategory(peripherals);
            productService.saveProduct(peripheral4);

            Product peripheral5 = new Product(generateSKU(productService), "Genius USB Sp-Hf180 Black Speaker", 79.20, 2, ProductBrand.GENIUS, "The Genius USB SP-HF180 Black Speaker is a USB-powered speaker system that offers a simple and convenient audio experience. With a compact design, it fits seamlessly into various setups. Powered through a USB connection, it eliminates the need for an external power source, providing hassle-free connectivity. The black color adds a modern touch to its appearance. While compact, the SP-HF180 is designed to deliver clear sound for users seeking an uncomplicated and space-saving speaker solution for their computers or other compatible devices. Always refer to the product specifications for detailed information on features and performance.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/cuwconz9qkhdxdgtrtpa", true);
            peripheral5.addCategory(peripherals);
            productService.saveProduct(peripheral5);

            Product peripheral6 = new Product(generateSKU(productService), "Luxemate Q8000 Tkl Black Wireless Genius Keyboard and Mouse Kit", 88.00, 4, ProductBrand.GENIUS, "The LuxeMate Q8000 TKL Black Wireless Genius Keyboard and Mouse Kit offer a wireless and space-saving solution for users. The TenKeyLess (TKL) design of the keyboard reduces its footprint, making it ideal for those with limited desk space. The wireless connectivity ensures freedom of movement, while the black color adds a sleek and modern aesthetic. The kit includes both a wireless keyboard and mouse, providing a unified and convenient input experience. With this kit, users can enjoy the benefits of wireless functionality without compromising on style and functionality. Always refer to the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/sullcn1qmazcp5mfnftd", true);
            peripheral6.addCategory(peripherals);
            productService.saveProduct(peripheral6);

            Product peripheral7 = new Product(generateSKU(productService), "Webcam Genius Ecam M8000", 125.40, 1, ProductBrand.GENIUS, "The Genius eCam M8000 is a webcam offering reliable video capture and communication capabilities. With its high-quality imaging sensor, it provides clear and sharp visuals, making it suitable for video conferencing, live streaming, and online content creation. The webcam is likely equipped with features such as autofocus and low-light sensitivity to ensure optimal performance in various lighting conditions. Its compact and user-friendly design makes it an accessible solution for users seeking a dependable webcam for their communication and content creation needs. Always refer to the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/va7lzjjlsxznzxvjzyrt", true);
            peripheral7.addCategory(peripherals);
            productService.saveProduct(peripheral7);

            Product peripheral8 = new Product(generateSKU(productService), "HypeRX Gaming Solocast Microphone Black PC/Mac/Ps4", 214.47, 2, ProductBrand.HYPERX, "The HyperX Gaming SoloCast Microphone in black is a reliable and versatile microphone suitable for use with PCs, Macs, and PS4 gaming consoles. With its sleek black design, it complements various setups. This microphone is specifically tailored for gaming and content creation, offering clear and crisp audio capture. Compatible with multiple platforms, it provides flexibility for users engaged in gaming, streaming, podcasting, or online communication. Its plug-and-play design makes it easy to set up, ensuring a seamless experience for users looking for a high-quality microphone solution. Always check the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/pyfgzq3dossxbfgkut05", true);
            peripheral8.addCategory(peripherals);
            productService.saveProduct(peripheral8);


            //DISKS (HDD/SSD)

            Product disk1 = new Product(generateSKU(productService), "HDD 1 Tb Western Digital WD Blue", 241.55, 2, ProductBrand.WESTERN_DIGITAL, "The WD Blue 1TB HDD by Western Digital is a robust hard disk drive designed for reliable storage. With a 1TB capacity, it provides ample space for storing documents, multimedia, and applications. As part of the WD Blue series, it is known for its balance of performance, reliability, and affordability. The drive is likely suitable for use in desktop computers or external enclosures, offering a straightforward and dependable storage solution for users seeking reliable and cost-effective storage options. Always check the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/wiuuk7mmaipomnpool5s", true);
            disk1.addCategory(disks);
            productService.saveProduct(disk1);

            Product disk2 = new Product(generateSKU(productService), "HDD 6 Tb Western Digital WD Sata III Purple", 787.52, 4, ProductBrand.WESTERN_DIGITAL, "The WD Purple 6TB HDD by Western Digital is a SATA III hard disk drive tailored for surveillance applications. With a substantial 6TB capacity, it provides ample storage space for extended video footage and recordings. The Purple series is optimized for continuous recording and playback in surveillance systems, featuring AllFrame technology for improved reliability and reduced frame loss. With SATA III connectivity, it ensures efficient data transfer rates. This drive is suitable for use in surveillance DVRs and NVRs, offering a reliable and high-capacity solution for video storage needs. Always refer to the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/pg4z4nyw3vv2rzs1xjql", true);
            disk2.addCategory(disks);
            productService.saveProduct(disk2);

            Product disk3 = new Product(generateSKU(productService), "HDD 3 Tb Seagate Ironwolf USD Sata III", 531.45, 1, ProductBrand.SEAGATE, "The Seagate IronWolf 3TB HDD with SATA III interface is specifically designed for NAS applications, providing a reliable and high-capacity storage solution. With a 3TB capacity, it offers ample space for data storage in a NAS environment. The IronWolf series is known for its durability and optimized performance in multi-drive NAS systems, featuring AgileArray technology for enhanced reliability and responsiveness. The SATA III interface ensures efficient data transfer rates. This drive is suitable for users looking to expand their NAS storage capacity with a drive that is built to handle the demands of 24/7 operation. Always refer to the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/wngoyxuw3g0gaffuri9l", true);
            disk3.addCategory(disks);
            productService.saveProduct(disk3);

            Product disk4 = new Product(generateSKU(productService), "SSD 240Gb Hikvision E1000 M2 Nvme (256Gb)", 69.27, 3, ProductBrand.HIKVISION, "The Hikvision E1000 240GB M.2 NVMe SSD is a solid-state drive that utilizes NVMe (Non-Volatile Memory Express) technology, offering fast read and write speeds for quick data access. With a capacity of 240GB, it provides a balance of performance and storage space, suitable for use in various computing applications. The M.2 form factor makes it well-suited for compact systems, and NVMe ensures that it takes full advantage of the high-speed capabilities of modern storage interfaces. This SSD is designed to enhance overall system responsiveness and is ideal for users looking to upgrade their storage solution to a faster and more efficient drive. Always check the product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/ej6exqyxinmquwu4ec26", true);
            disk4.addCategory(disks);
            productService.saveProduct(disk4);

            Product disk5 = new Product(generateSKU(productService), "SSD 480Gb Western Digital WD Sata III Green", 118.80, 7, ProductBrand.WESTERN_DIGITAL, "The Western Digital WD Green 480GB SATA III SSD is a solid-state drive designed for efficient and fast storage. With a 480GB capacity, it offers a balance of performance and storage space, making it suitable for various computing needs. The SATA III interface ensures compatibility with a wide range of systems, providing faster data transfer rates than traditional hard drives. SSDs like this one are known for their speed, reliability, and energy efficiency, contributing to improved overall system responsiveness. Always check the latest product specifications from the manufacturer for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/fnd9npxakxxhfvhwt0dt", true);
            disk5.addCategory(disks);
            productService.saveProduct(disk5);


            //COMPUTER TOWER
            Product computerTower1 = new Product(generateSKU(productService), "Aconcawa Computer Tower Ultimate Gaming Series RGB Source 600w (Bz-200)", 211.17, 10, ProductBrand.ACONCAGUA, "Immerse yourself in the sleek aesthetics of this case featuring a stylish black front panel complemented by a vibrant RGB LED strip. Experience enhanced cooling with a pre-installed 120mm rear fan that not only ensures optimal airflow but also adds a touch of RGB illumination to your setup.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/h9rkixnbgwp1gfysjceq", true);
            computerTower1.addCategory(computerTower);
            productService.saveProduct(computerTower1);

            Product computerTower2 = new Product(generateSKU(productService), "Gamemax Panda T802 Computer Tower W/Cooler ARGB", 221.07, 4, ProductBrand.GAMEMAX, "The GameMax Panda T802 Computer Tower with ARGB Cooler is a gaming chassis featuring vibrant Addressable RGB lighting for customizable visual effects. Designed with a focus on both aesthetics and functionality, the tower includes an ARGB cooler for efficient thermal management, catering to the needs of gamers. With compatibility for gaming components and a commitment to build quality, the Panda T802 provides a stylish and performance-oriented solution for creating an immersive gaming setup.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/acmxncvbegvos8pa6i6i", true);
            computerTower2.addCategory(computerTower);
            productService.saveProduct(computerTower2);

            Product computerTower3 = new Product(generateSKU(productService), "Cooler Master NR200p Computer Tower + Riser", 211.17, 6, ProductBrand.COOLER_MASTER, "The Cooler Master NR200P Computer Tower is a compact case known for its flexibility and performance. With a focus on efficient use of space, it offers a compact form factor suitable for various setups. The inclusion of a riser indicates support for vertical GPU mounting, allowing users to showcase their graphics card for a visually striking build. Cooler Master is recognized for its commitment to quality and innovative design, and the NR200P is likely to feature excellent cooling options and cable management. This case is suitable for users seeking a compact yet powerful solution for gaming or high-performance computing setups. Always refer to the latest product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/jlk9ccr53nw0cnth6mcn", true);
            computerTower3.addCategory(computerTower);
            productService.saveProduct(computerTower3);

            Product computerTower4 = new Product(generateSKU(productService), "Thermaltake Ah T200 TG Computer Tower Black", 445.47, 8, ProductBrand.THERMALTAKE, "The Thermaltake AH T200 TG Computer Tower in black is a gaming case that stands out with its distinctive design. The use of tempered glass panels provides a clear view of the internal components, allowing users to showcase their gaming hardware. The case is likely equipped with features for efficient cooling, cable management, and component compatibility. Its black color adds a sleek and modern aesthetic to the overall setup. This tower is suitable for gamers and enthusiasts looking to create a bold and eye-catching gaming rig. Always refer to the latest product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/o4w6kzufioseisskbkgr", true);
            computerTower4.addCategory(computerTower);
            productService.saveProduct(computerTower4);

            Product computerTower5 = new Product(generateSKU(productService), "Computer Tower Kit Slim - Various Brands", 231.00, 2, ProductBrand.VARIOUS, "The Computer Tower Kit Slim, available from various brands, offers a space-efficient and compact solution for building small desktop computers. These kits typically include essential components like the slim tower chassis and a power supply unit, catering to users seeking a sleek and minimalist form factor. The versatility of these kits allows for a range of hardware configurations, making them suitable for general computing purposes or as media center solutions. Users benefit from the variety of brands available, each providing unique designs and features to meet diverse preferences and requirements. It's important to review specific inclusions and compatibility details provided by the chosen brand before making a purchase.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/emmn3yu62wc4uyyp84sb", true);
            computerTower5.addCategory(computerTower);
            productService.saveProduct(computerTower5);

            Product computerTower6 = new Product(generateSKU(productService), "Computer Tower Cooler Master NR 200 Black", 197.97, 12, ProductBrand.COOLER_MASTER, "The Cooler Master NR200 Black Computer Tower is a compact case known for its flexibility and efficient use of space. Designed for gaming enthusiasts and high-performance computing, it features a sleek black design. The case likely offers support for various cooling solutions and efficient cable management. Cooler Master is renowned for its commitment to quality, and the NR200 model is expected to deliver on build quality and functionality. This tower is suitable for users seeking a compact yet powerful solution for gaming or high-performance computing setups. Always refer to the latest product specifications for detailed information on features and compatibility.", "https://res.cloudinary.com/dryi0j55n/image/upload/f_auto,q_auto/v1/tech/nb0bhfjog74cp9hascjl", true);
            computerTower6.addCategory(computerTower);
            productService.saveProduct(computerTower6);


            ///REVIEW Y FAVORITES
            Review reviewMotherBoard1 = new Review("Overall, the Gigabyte H310M is a solid choice for those looking for a reliable and affordable motherboard for a basic or everyday system, but it may not be the best choice for users looking for advanced functionality or significant expansion plans in the future.", 4);
            customerAlejandra.addReview(reviewMotherBoard1);
            motherBoard1.addReview(reviewMotherBoard1);
            reviewService.save(reviewMotherBoard1);

            Review reviewMotherBoard1Pablo = new Review("The Gigabyte H310M motherboard is a budget-friendly but limited option for entry-level computer setups. Although it offers support for 8th and 9th generation Intel processors, its standard feature set and limitations may disappoint users looking for more advanced performance or additional functionality..", 2);
            customerPablo.addReview(reviewMotherBoard1Pablo);
            motherBoard1.addReview(reviewMotherBoard1Pablo);
            reviewService.save(reviewMotherBoard1Pablo);

            Review reviewDestopk1 = new Review("The 22-inch monitor is functional and offers a decent viewing experience for general tasks. Although for more immersive experiences, especially in gaming or design work, a larger screen might be preferable.\n" +
                    "\n" +
                    "In short, this set offers satisfactory performance for everyday users looking for a functional PC at a reasonable price. It's a good option for students or casual users, although those with more demanding needs may need to make some upgrades in the future to accommodate more intensive tasks", 3);
            customerAlejandra.addReview(reviewDestopk1);
            desktop1.addReview(reviewDestopk1);
            reviewService.save(reviewDestopk1);

            Review reviewNotebook1 = new Review("I recently purchased the Aiwa Cloudbook CA141-C, and I must say, I'm thoroughly impressed with its performance. The sleek design coupled with its lightweight build makes it incredibly convenient for my on-the-go lifestyle. The 14-inch display provides a fantastic visual experience, whether I'm working on spreadsheets, watching movies, or simply browsing the web.", 4);
            customerAlejandra.addReview(reviewNotebook1);
            notebook1.addReview(reviewNotebook1);
            reviewService.save(reviewNotebook1);

            Review reviewperipheral1= new Review("I have had the opportunity to use the HDC Kb113 USB keyboard and I must say that it has exceeded my expectations for its performance and comfort on a daily basis.\n" +
                    "\n" +
                    "The first thing I highlight is its ergonomic and compact design, perfect for saving space on my desk. The keys have a standard layout and are comfortable to use, making my typing experience smooth and precise.",5);
            customerAlejandra.addReview(reviewperipheral1);
            peripheral1.addReview(reviewperipheral1);
            reviewService.save(reviewperipheral1);


            Favorite favoriteAleNote = new Favorite(customerAlejandra, notebook1);
            customerAlejandra.addFavorite(favoriteAleNote);
            notebook1.addFavorite(favoriteAleNote);
            favoriteService.save(favoriteAleNote);

            Favorite favoriteAleRam = new Favorite(customerAlejandra, ram3);
            customerAlejandra.addFavorite(favoriteAleRam);
            ram3.addFavorite(favoriteAleRam);
            favoriteService.save(favoriteAleRam);

            Favorite favoriteAleComputer = new Favorite(customerAlejandra, computerTower2);
            customerAlejandra.addFavorite(favoriteAleComputer);
            computerTower2.addFavorite(favoriteAleComputer);
            favoriteService.save(favoriteAleComputer);

            Favorite favoriteAlecomputer6 = new Favorite(customerAlejandra, computerTower6);
            customerAlejandra.addFavorite(favoriteAlecomputer6);
            computerTower6.addFavorite(favoriteAlecomputer6);
            favoriteService.save(favoriteAlecomputer6);
        };
    }
}
