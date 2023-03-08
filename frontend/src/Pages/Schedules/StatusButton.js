// import React from "react";
// import { Button } from "semantic-ui-react";

// export function StatusButton() {
//   const schedules = [
//     {
//       id: 1,
//       name: "Pirmas tvarkaraštis",
//       dateFrom: "2016-01-04 10:34:23",
//       dateUntil: "2016-06-04 10:34:23",
//       status: "invalid",
//     },
//     {
//       id: 2,
//       name: "Antras tvarkaraštis",
//       dateFrom: "2017-01-04 10:34:23",
//       dateUntil: "2018-06-04 10:34:23",
//       status: "attention",
//     },
//     {
//       id: 3,
//       name: "Trečias tvarkaraštis",
//       dateFrom: "2019-01-04 10:34:23",
//       dateUntil: "2023-06-04 10:34:23",
//       status: "valid",
//     },
//     {
//       id: 4,
//       name: "Ketvirtas tvarkaraštis",
//       dateFrom: "2022-01-04 10:34:23",
//       dateUntil: "2023-06-04 10:34:23",
//       status: "valid",
//     },
//   ];

//   const MyStatus = () => {
//     if (schedules.status === "valid") {
//       return (
//         <>
//           <Button 
//           id="okey" 
//           basic 
//           compact 
//           icon="check" 
//           title="Statusas" />
//         </>
//       );
//     } else if (schedules.status === "invalid") {
//       return (
//         <>
//           <Button
//             id="grey"
//             basic
//             compact
//             icon="clock outline"
//             title="Statusas"
//           />
//         </>
//       );
//     } else {
//       return (
//         <>
//           <Button
//             id="attention"
//             basic
//             compact
//             icon="attention"
//             title="Statusas"
//           />
//         </>
//       );
//     }
//   };

//   return <MyStatus />;
// }
