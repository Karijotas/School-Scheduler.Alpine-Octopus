import { useState } from "react";

const JSON_HEADERS = {
    "Content-Type": "application/json",
  };

  export function ViewHolidays() {
const [holidays,setHolidays]= useState([]);
const [active, setActive] = useState("");;
const [activePage, setActivePage] = useState(0);
const [nameText, setNameText] = useState("");
const [buildingText, setBuildingText] = useState("");
const [pagecount, setPageCount] = useState();
const [holidaysforPaging, setHolidaysForPaging] = useState([]);

const removeHoliday = (id) => {
    fetch("/api/v1/holidays/delete/" + id, {
        method: "PATCH",
    })
      .then(fetchRooms)
      .then(setOpen(false));
  };
  }