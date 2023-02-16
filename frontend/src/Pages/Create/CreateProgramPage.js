import React, { useState, useEffect } from "react";
import {
  Button,
  Form,
  Icon,
  Input,
  Image,
  Grid,
  TextArea,
  List,
  Select,
} from "semantic-ui-react";

import { ViewPrograms } from "../Edit/EditPages/ViewPrograms";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

const yearOptions = [
  { key: 23, value: 2023, text: "2023" },
  { key: 24, value: 2024, text: "2024" },
  { key: 25, value: 2025, text: "2025" },
  { key: 26, value: 2026, text: "2026" },
  { key: 27, value: 2027, text: "2027" },
  { key: 28, value: 2028, text: "2028" },
];

export function CreateProgramPage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = [];
  const [error, setError] = useState();
  const [hours, setHours] = useState("");

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createProgram = () => {
    fetch("/api/v1/programs", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult);
  };

  useEffect(() => {
  fetch("/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const addSubjectHours = () => {
    fetch("/api/v1/programs/{programId}/subjects/newSubjectsWithHours", {
      method: "PATCH",
      headers: JSON_HEADERS,
      body: JSON.stringify(subjectHours),
    })
      .then((result) => {
        if (!result.ok) {
          setError("Update failed");
        } else {
          setError();
        }
      })
      .then(applyResult);
  };
  return (
    <div>
      {!hide && (
        <div className="create-new-page">
          <Form>
            <Form.Field>
              <label>Programos pavadinimas</label>
              <input
                placeholder="Programos pavadinimas"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Field>
            <Form.Group widths="equal">
              <Form.Field>
                <label>Aprašymas</label>
                <TextArea
                  placeholder="Aprašymas"
                  style={{ minHeight: 100 }}
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </Form.Field>
            </Form.Group>
            
            <div>
              <Button
                icon
                labelPosition="left"
                className=""
                onClick={() => setHide(true)}
              >
                <Icon name="arrow left" />
                Atgal
              </Button>
              <Button
                type="submit"
                className="controls"
                primary
                onClick={createProgram}
              >
                Sukurti
              </Button>
            </div>
          </Form>
        </div>
      )}
      {hide && (
        <div>
          <ViewPrograms />
        </div>
      )}
    </div>
  );
}
