package com.example.se306project1.dataproviders;

import com.example.se306project1.R;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//this class will create all the product data
public class ProductData {

    public static List<IProduct> getAllProducts(){
        List<IProduct> products = new ArrayList<>();
        IProduct product1 = new Product("technic",
                "Dodge Charger",
                "  Give fans of Fast & Furious the ultimate thrill with this LEGO® Technic™ Dom’s Dodge Charger (42111) building set for kids and adults. Based on the iconic 1970s Dodge Charger R/T, it’s packed with authentic details. The opening hood reveals a model version of the iconic V8 engine. Other cool features include moving pistons, wishbone suspension, steering system and air blower. There are even nitro bottles in the trunk to bring Dom's daring high-speed chases to life. Just like the real thing! Fans aged 10 and up will love building their very own replica model of Dominic Toretto’s famous car.",
                159,
                new ArrayList<>(Arrays.asList(R.drawable.dodgecharger1,R.drawable.dodgecharger2,R.drawable.dodgecharger3)),
                100,
                0);
        products.add(product1);

        IProduct product2 = new Product("technic",
                "Ducati",
                "  Style, sophistication, performance. With breathtaking looks, this LEGO® Technic™ 42107 Ducati Panigale V4 R captures the spirit and design of the original. It’s the first model motorcycle in LEGO Technic history to include a gearbox for exploring different speeds and techniques. Other amazing features include steering, front and rear suspension for realistic movement, plus front and rear disc brakes.",
                135,
                new ArrayList<>(Arrays.asList(R.drawable.ducati1,R.drawable.ducati2,R.drawable.ducati3)),
                100,
                0);
        products.add(product2);


        IProduct product3 = new Product("technic",
                "Ferrari",
                "  From the LEGO® Technic™ Ultimate Car Concept series comes an impressive build for adult Ferrari fans. Enter a zone of total mindfulness as you recreate the features of the Ferrari Daytona SP3 model in classic red with chrome-painted rims. Then place it on display to admire the iconic rear and curved lines that make this 1:8 scale model so special.",
                188,
                new ArrayList<>(Arrays.asList(R.drawable.ferrari1,R.drawable.ferrari2,R.drawable.ferrari3)),
                100,
                0);
        products.add(product3);

        IProduct product4 = new Product("technic",
                "helicopter",
                " ",
                163,
                new ArrayList<>(Arrays.asList(R.drawable.helicopter1,R.drawable.helicopter2,R.drawable.helicopter3)),
                100,
                0);
        products.add(product4);

        IProduct product5 = new Product("technic",
                "HoverCraft",
                "  Looking for the perfect gift for kids who enjoy exciting vehicles? They’re sure to love this LEGO® Technic™ Rescue Hovercraft (42120) toy. Filled with authentic features, it’s perfect for recreating daring missions. Boys and girls aged 8 and up will love seeing the toy vehicle ‘hover' as it rolls on concealed wheels. With turning fans, a warning light, cockpit with seats, control panel and steering handlebar, there's lots to discover. And when kids are ready for a new challenge, the hovercraft toy rebuilds into a Twin-Engine Aircraft, packed with realistic features, including moving rudders and moving ailerons.",
                116,
                new ArrayList<>(Arrays.asList(R.drawable.hovercraft1,R.drawable.hovercraft2,R.drawable.hovercraft3)),
                100,
                0);
        products.add(product5);

        IProduct product6 = new Product("technic",
                "LandRover",
                "  Experience world-leading vehicle design firsthand with this highly authentic and displayable 42110 LEGO® Technic™ Land Rover Defender model. Developed in partnership with Land Rover, this impressive LEGO replica captures the vehicle’s outstanding level of refinement with its clean, modern lines and sculpted surfaces, and comes with original-design rims with ground-gripping tires, plus a host of realistic features and functions",
                108,
                new ArrayList<>(Arrays.asList(R.drawable.landrover1,R.drawable.landrover2,R.drawable.landrover3)),
                100,
                0);
        products.add(product6);

        IProduct product7 = new Product("technic",
                "SkidSteer Loader",
                "  If you’re looking for a gift for kids who love construction-site role play, this LEGO® Technic™ Skid Steer Loader (42116) is sure to keep them busy. Boys and girls aged 7 and up will enjoy building the loader – and that’s just the start! Once they’ve built it, they can create endless construction adventures as they check out the vehicle's realistic features. A 2-function bucket for scooping, operator's cab, control panel, roll cage and front and rear lights give kids everything they need for hours of creative play.",
                131,
                new ArrayList<>(Arrays.asList(R.drawable.skidsteerloader1,R.drawable.skidsteerloader2,R.drawable.skidsteerloader3)),
                100,
                0);
        products.add(product7);

        IProduct product8 = new Product("technic",
                "Telehandler",
                "  Kids aged 7+ who love construction toys and tow trucks can have lots of fun with this 2-in-1 building set. The LEGO® Technic™ Telehandler (42133) buildable model comes with realistic features like the steering, lifting arm and tilting forks. There’s also a buildable pallet to lift, so kids can role-play different construction site adventures.",
                99,
                new ArrayList<>(Arrays.asList(R.drawable.telehandler1,R.drawable.telehandler2,R.drawable.telehandler3)),
                100,
                0);
        products.add(product8);

        IProduct product9 = new Product("technic",
                "Tow Truck",
                "  Open up the world of engineering with this LEGO® Technic Heavy-duty Tow Truck (42128) model. Packed with details, it’s a great tribute to the world’s best-loved tow trucks. Check out its authentic grille, air filters, lights, exhaust pipe, and fresh colour scheme. Then explore all the pneumatic and mechanical functions that make this model so realistic.",
                122,
                new ArrayList<>(Arrays.asList(R.drawable.towtruck1,R.drawable.towtruck2,R.drawable.towtruck3)),
                100,
                0);
        products.add(product9);

        IProduct product10 = new Product("technic",
                "Transformation Vehicle",
                "  Kids aged 9+ can enjoy twice the fun with this LEGO® Technic™ App-Controlled Transformation Vehicle 42140 set. In a first for LEGO Technic sets, this vehicle toy flips over when it drives into a wall to reveal the second vehicle. No wall? No problem! Kids can turn the model over to switch vehicles. On one side is an aerodynamic tracked racer. On the other side is a tracked exploration vehicle. With suspension, a cockpit and tracks on both vehicles, plus a truck bed on the exploration truck, there’s lots for kids to discover.",
                114,
                new ArrayList<>(Arrays.asList(R.drawable.transformationvehicle1,R.drawable.transformationvehicle2,R.drawable.transformationvehicle3)),
                100,
                0);
        products.add(product10);


        IProduct product11 = new Product("starWar",
                "Attack Shuttle",
                "  Kids will love roleplaying Clone Force 99 missions with this LEGO® brick-built version of The Bad Batch Attack Shuttle (75314) from Star Wars: The Bad Batch. It features large wings that move up and down for landing and flight modes, 2 spring-loaded shooters and an opening dual LEGO minifigure cockpit and rear cabin with space for 2 LEGO minifigures and weapons storage. Pull up the central dorsal fin to access the interior for easy play",
                127,
                new ArrayList<>(Arrays.asList(R.drawable.attackshuttle_pic1,R.drawable.attackshuttle_pic2,R.drawable.attackshuttle_pic3)),
                100,
                0);
        products.add(product11);

        IProduct product12 = new Product("starWar",
                "Boba Fett",
                "  Write new chapters in the Star Wars: The Book of Boba Fett saga with this Boba Fett’s Throne Room buildable playset (75326) for fans aged 9 and up. The brick-built palace model opens up for easy access to the detailed throne room, barbecue area and kitchen. There is a throne with a hidden treasure compartment and a pop-up function to eject Bib Fortuna, tilting steps, an opening gate and lots of playful accessories.",
                138,
                new ArrayList<>(Arrays.asList(R.drawable.bobafett_pic1,R.drawable.bobafett_pic2,R.drawable.bobafett_pic3)),
                100,
                0);
        products.add(product12);

        IProduct product13 = new Product("starWar",
                "Dark Trooper Attack",
                "  Celebrate the return of Luke Skywalker in Star Wars: The Mandalorian Season 2 with this buildable Dark Trooper Attack playset (75324) for fans aged 8+. It features an authentically detailed recreation of the scene inside the Imperial Light Cruiser where Luke reappeared, with a revolving elevator, rotating battle platform and a sliding ‘Force’ platform to fend off Dark Troopers. There are 4 LEGO® minifigures in the set: a new-for-March-2022 Luke Skywalker with a lightsaber, and 3 Dark Troopers, each with blaster rifles, to inspire fun, creative role play.",
                85,
                new ArrayList<>(Arrays.asList(R.drawable.darktrooperattack_pic1,R.drawable.darktrooperattack_pic2,R.drawable.darktrooperattack_pic3)),
                100,
                0);
        products.add(product13);

        IProduct product14 = new Product("starWar",
                "Darth Vader",
                "  Rule with a plastic fist with this buildable and highly posable Darth Vader figure. This impressive figure features detailed armor and chest decoration, textile tunic and cape, and a detachable Lightsaber. Imperial leaders will love swinging his arm in battle using the wheel on his back, and lifting Lord Vader's helmet to reveal the scarred face of fallen Jedi warrior, Anakin Skywalker.",
                92,
                new ArrayList<>(Arrays.asList(R.drawable.darthvader_pic1,R.drawable.darthvader_pic2,R.drawable.darthvader_pic3)),
                100,
                0);
        products.add(product14);

        IProduct product15 = new Product("starWar",
                "Land speeder",
                "  Be transported to the desert planet of Tatooine as you build the first-ever LEGO® Star Wars™ Ultimate Collector Series version of Luke Skywalker’s Landspeeder (75341). Use new building techniques and custom-made LEGO elements to recreate this iconic vehicle in intricate detail. From the cockpit windscreen to the turbine engine missing its cover, it has everything you remember from Star Wars: A New Hope.",
                107,
                new ArrayList<>(Arrays.asList(R.drawable.landspeeder_pic1,R.drawable.landspeeder_pic2,R.drawable.landspeeder_pic3)),
                100,
                0);
        products.add(product15);

        IProduct product16 = new Product("starWar",
                "Rey",
                "  This LEGO® BrickHeadz™ 41602 Rey construction character is fun to build using colorful LEGO bricks that recreate all of her iconic details from the blockbuster Star Wars: The Last Jedi movie, including decorated torso and belt. She also has a detachable Lightsaber and stands on a buildable collector's baseplate with BrickHeadz icon for easy display in your home, office or anywhere you like.",
                121,
                new ArrayList<>(Arrays.asList(R.drawable.rey_pic1,R.drawable.rey_pic2,R.drawable.rey_pic3)),
                100,
                0);
        products.add(product16);

        IProduct product17 = new Product("starWar",
                "Stormtrooper",
                "  This LEGO® BrickHeadz™ construction character featuring a Stormtrooper is fun to build using LEGO bricks that recreate all his iconic details from the original blockbuster Star Wars™: Episode V The Empire Strikes Back movie, including his white helmet and armor. The Stormtrooper figure also has a detachable blaster and stands on a buildable collector's baseplate with BrickHeadz icon for easy display in your home, office or anywhere you like.",
                142,
                new ArrayList<>(Arrays.asList(R.drawable.stormtrooper_pic1,R.drawable.stormtrooper_pic2,R.drawable.stormtrooper_pic3)),
                100,
                0);
        products.add(product17);

        IProduct product18 = new Product("starWar",
                "The Child",
                "  Turn the cuteness factor up to the max with this LEGO® Star Wars™ The Child (75318) build-and-display model. Authentic details of this popular character, affectionately known as Baby Yoda, are recreated in LEGO style, with posable head, ears and mouth for different expressions, plus the Child’s favourite toy – a gearshift knob (element included) – for it to hold just like it does in Star Wars: The Mandalorian.",
                78,
                new ArrayList<>(Arrays.asList(R.drawable.thechild_pic1,R.drawable.thechild_pic2,R.drawable.thechild_pic3)),
                100,
                0);
        products.add(product18);

        IProduct product19 = new Product("starWar",
                "X-Wing",
                "  Let youngsters learn to build and role-play with this LEGO® Star Wars™ Resistance X-wing (75297) for ages 4 and up. A simple-to-build version of the starfighter from Star Wars: The Force Awakens, this awesome building toy has wings that fold out for flight and in for landing. The Poe Dameron LEGO minifigure, which comes with a blaster pistol, fits in the X-wing's opening cockpit and there is space behind for the BB-8 droid LEGO figure.",
                102,
                new ArrayList<>(Arrays.asList(R.drawable.xwing_pic1,R.drawable.xwing_pic2,R.drawable.xwing_pic3)),
                100,
                0);
        products.add(product19);

        IProduct product20 = new Product("starWar",
                "Yoda",
                "  Add to any fan’s collection with two Yoda LEGO® Star Wars™ characters in one set! This intricately detailed 75255 display model of powerful Jedi Master Yoda is based on the character from Star Wars: Attack of the Clones, and includes a posable head and eyebrows, moving fingers and toes, and Yoda's signature green Lightsaber. This characterful LEGO Star Wars figure also includes a fact plaque with details about Yoda and a stand to mount the included Yoda minifigure with Lightsaber, making for an ideal Star Wars collectible for any fan.",
                93,
                new ArrayList<>(Arrays.asList(R.drawable.yoda_pic1,R.drawable.yoda_pic2,R.drawable.yoda_pic3)),
                100,
                0);
        products.add(product20);

        IProduct product21 = new Product("city",
                "Hospital",
                "  This LEGO® City Hospital (60330) toy playset is packed with inspiring features for imaginative play, including a front desk with kids’ playroom, maternity ward, WC and an MRI scan room. The set also includes an ambulance and rescue helicopter and comes with Road Plates for connection to other LEGO sets, plus 12 minifigures, including 4 characters from the LEGO City Adventures TV series.",
                136,
                new ArrayList<>(Arrays.asList(R.drawable.hospital_pic1,R.drawable.hospital_pic2,R.drawable.hospital_pic3)),
                100,
                0);
        products.add(product21);

        IProduct product22 = new Product("city",
                "Ice Cream Truck",
                "  Children can become everyday heroes with this fun LEGO® City Ice-Cream Truck (60253) playset. Kids get to save the day by serving cool ice-cream cones and popsicles to sweltering LEGO City citizens! And with room inside the truck for the ice-cream lady to work and drive, plus a skateboarding customer with a cute dog, there's plenty of scope for independent role-play fun",
                154,
                new ArrayList<>(Arrays.asList(R.drawable.icecreamtruck_pic1,R.drawable.icecreamtruck_pic2,R.drawable.icecreamtruck_pic3)),
                100,
                0);
        products.add(product22);

        IProduct product23 = new Product("city",
                "police Station",
                "  This 3-level LEGO® City Police Station (60316) toy is packed with inspiring details, including a jail with a breakout function, a crooks’ exercise yard and a dog training area. Kids also get a police car, helicopter and a crook’s customized garbage truck, plus a Road Plate for connection to other playsets. Just add the 5 minifigures, including 3 LEGO City Adventures TV series characters, and let the play begin.",
                174,
                new ArrayList<>(Arrays.asList(R.drawable.policestation_pic1,R.drawable.policestation_pic2,R.drawable.policestation_pic3)),
                100,
                0);
        products.add(product23);

        IProduct product24 = new Product("city",
                "Road Plates",
                " This LEGO® City Road Plates (60304) building set adds a realistic backdrop to kids’ imaginative play, with glow-in-the-dark toy streetlights, traffic lights, road signs, traffic lane markings, speed bumps, a crosswalk, trees and greenery. Kids can place the Road Plates their way, build a city around them and connect to other LEGO Road Plate building sets to expand their city creations!",
                158,
                new ArrayList<>(Arrays.asList(R.drawable.roadplates_pic1,R.drawable.roadplates_pic2,R.drawable.roadplates_pic3)),
                100,
                0);
        products.add(product24);

        IProduct product25 = new Product("city",
                "School Day",
                "  Exciting school adventures await with this LEGO® City School Day (60329) playset, featuring 2 classrooms and a yard with a climbing wall and hopscotch, plus a toy school bus and bicycle. The Road Plates, with crosswalk and bike lane, can also be used to connect to other LEGO sets, and with 7 minifigures, including 2 LEGO City Adventures TV series TV characters, there’s plenty of inspiration for imaginative play.",
                127,
                new ArrayList<>(Arrays.asList(R.drawable.schoolday_pic1,R.drawable.schoolday_pic2,R.drawable.schoolday_pic3)),
                100,
                0);
        products.add(product25);

        IProduct product26 = new Product("city",
                "Shopping Mall",
                "  Delight shopping-crazy kids with the Heartlake City Shopping Mall (41450) toy. It comes with 6 characters, including a new-for-January-2021 micro-doll. Kids will love exploring the stores and food court with Emma and her family.",
                166,
                new ArrayList<>(Arrays.asList(R.drawable.shoppingmall_pic1,R.drawable.shoppingmall_pic2,R.drawable.shoppingmall_pic3)),
                100,
                0);
        products.add(product26);

        IProduct product27 = new Product("city",
                "Stunt Park",
                " This LEGO® City Stunt Park (60293) playset comes with a toy flywheel-powered motorcycle for spectacular stunt action. Kids activate the bike to send it soaring through an array of awesome props and challenges and can quickly reconfigure the stunt modules for different competitions and performances.",
                123,
                new ArrayList<>(Arrays.asList(R.drawable.stuntpark_pic1,R.drawable.stuntpark_pic2,R.drawable.stuntpark_pic3)),
                100,
                0);
        products.add(product27);

        IProduct product28 = new Product("city",
                "Town Center",
                "  Kids get to dive into the LEGO® City Adventures TV series with this premium-quality LEGO City Town Center (60292) playset. There’s a toy pizza restaurant, recycling station, car wash, EV charging station, martial arts dojo and a park with a kids’ ride, plus fun LEGO City minifigure characters. And with the included LEGO City Road Plates, kids can connect to other playsets to expand their city – just the way they like it! A great gift for kids aged 6 and up.",
                104,
                new ArrayList<>(Arrays.asList(R.drawable.towncenter_pic1,R.drawable.towncenter_pic2,R.drawable.towncenter_pic3)),
                100,
                0);
        products.add(product28);

        IProduct product29 = new Product("city",
                "Train Station",
                "  This LEGO® City Train Station (60335) set is packed with cool features, including a ticket office, coffee bar, control room, platform and a track piece that’s compatible with LEGO City train sets. Kids also get a toy bus, road-and-rail cherry-picker truck with a portable toilet in tow, plus a grade crossing with a LEGO City Road Plate for connection to other City sets. Just add the 6 minifigure characters and let the adventures begin.",
                132,
                new ArrayList<>(Arrays.asList(R.drawable.trainstation_pic1,R.drawable.trainstation_pic2,R.drawable.trainstation_pic3)),
                100,
                0);
        products.add(product29);

        IProduct product30 = new Product("city",
                "WildlifeRescueCamp",
                "  This LEGO® City Wildlife Rescue Camp (60307) toy playset is packed with fun features. There’s a well-equipped treehouse-camp with a watchtower, plus a floating dinghy, mobile research lab, motorbike and an ultralight. Kids also get 6 minifigures, including LEGO City TV character Sleet, plus elephant, lion, lioness, lion cub, eagle and monkey figures for fun-packed wildlife adventures.",
                156,
                new ArrayList<>(Arrays.asList(R.drawable.wildliferescuecamp_pic1,R.drawable.wildliferescuecamp_pic2,R.drawable.wildliferescuecamp_pic3)),
                100,
                0);
        products.add(product30);

        return products;
    }


}
