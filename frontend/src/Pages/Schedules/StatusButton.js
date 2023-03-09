import React, { useEffect, useState } from "react";
import { Button } from "semantic-ui-react";

export function StatusButton() {
  const [schedules, setSchedules] = useState([]);
  const [activePage, setActivePage] = useState(0);

  const fetchSchedules = async () => {
    fetch("/api/v1/schedule/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse));
  };

  const MyStatus = () => {
    if (schedules.status==="Valid") {
      return (
        <>
          <Button id="okey" basic compact icon="check" title="Statusas" />
        </>
      );
    } else if (schedules.status==="Attention") {
      return (
        <>
          <Button
            id="attention"
            basic
            compact
            icon="attention"
            title="Statusas"
          />
        </>
      );
    } else {
      return (
        <>
          <Button
            id="grey"
            basic
            compact
            icon="clock outline"
            title="Statusas"
          />
        </>
      );
    }
  };

  return MyStatus();
}
