## VIDEO LINK:
https://drive.google.com/file/d/1lNkvYJa4MilU4ZellGJlrrhYTi5D_E5Z/view
Length: 3:26

## Inspiration
According to an analysis done by JAMA health, during the pandemic, on average, children's average screen usage increased 1.5 times, from 162 minutes a day to 246 minutes. Dr. Beurkens says more time spent on devices, leads to less time for physical activities, worse eyesight, higher levels of anxiety and stress, attention and focus issues, and other health problems. Additionally, phones are tempting to use. Once you pick up a phone, social media app algorithms keep you hocked on for hours at end. Pirate Time encourages you to put your phone down and collect rewards for staying focused.

## What it does
Pirate Time allows users to set a screentime limit where they are not allowed to use certain apps or are just restricted to using certain apps, depending on the setting. The longer screentime set, the more coins the pirate find at the end of their journey. However, if the user uses a restricted app during their screen time journey, they don't receive any coins. Users can compete with friends to see who can collect the most coins!

## How we built it
We used Android Studio and Java to create our app, Pirate Time. Screen Usage data is collected using the UsageStatsManager and UsageStats Library for Android and the map is rendered using the openstreetmap API. Additionally, a random point is chosen for the pirate to complete the journey based on old shipwreck data from The National Oceanic and Atmospheric Administration.

## Challenges we ran into
Originally we were planning on using Google Maps API, however we needed to pay for this, so we switched to using openstreetmap. Another challenge we ran into was collecting the screen time data. The Android Studio Pixel Emulator had different app package names than the Samsung we used for final testing, so we had to change the app packages to collect the right app usage data. There were also several background apps which had originally messed up our screentime value, however, we removed these app packages from the screentime adding.

## What we learned + Accomplishments that we're proud of
Our entire team was using Android Studios for the first time and none of us had experience building our app before. We learned how to create a native app for Android We are proud that we were able to come out of the hackathon with a working app that accomplishes what we wanted, a pirate themed screen time app.

## What's next for Pirate Time
We hope to expand pirate time features to include more games and journeys. This way users could even set bed time limits to not use their phone after a certain time. In the future, users may also be able to challenge their friends to see who uses their phone least.
