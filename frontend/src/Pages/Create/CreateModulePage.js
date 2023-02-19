import React, { useEffect, useState } from "react";
import { Button, Form, Icon, Input, Select } from "semantic-ui-react";
import { ViewModules } from "../Edit/EditPages/ViewModules";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateModulePage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modules, setModules] = useState("");

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

  const createModule = () => {
    fetch("/api/v1/modules", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult);
  };

  // useEffect(() => {
  //   fetch("/api/v1/modules/")
  //     .then((response) => response.json())
  //     .then((data) =>
  //       setModules(
  //         data.map((x) => {
  //           return { key: x.id, text: x.name, value: x.id };
  //         })
  //       )
  //     );
  // }, []);

  const fetchModules = async () => {
    fetch(`/api/v1/modules/`)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  };

  return (
    <div>
      {!hide && (
        <div className="create-new-page">
          <Form>
            <Form.Field>
              <label>Modulio pavadinimas</label>
              <input
                placeholder="Modulio pavadinimas"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Field>

            <Form.Field>
              <label>Aprašymas</label>
              <Input
                placeholder="Aprašymas"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </Form.Field>

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
                onClick={createModule}
              >
                Sukurti
              </Button>
            </div>
          </Form>
        </div>
      )}
      {hide && (
        <div>
          <ViewModules />
        </div>
      )}
    </div>
  );
}
