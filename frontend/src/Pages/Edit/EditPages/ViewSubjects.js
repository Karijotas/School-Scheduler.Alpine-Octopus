import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Icon,
  Input,
  Table,
} from "semantic-ui-react";
import { EditSubjectObject } from "./EditSubjectObject";
import { CreateSubjecPage } from "../../Create/CreateSubjectPage";
import "./ViewGroups.css";
import "./FilterSize.css";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewSubjects() {
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [nameText, setNameText] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [modules, setModules] = useState([]);
  const [subjectId, setSubjectId] = useState("");

  const [subjectsforPaging, setSubjectsForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchFilterSubjects = async () => {
    fetch(`/api/v1/subjects/page/name-filter/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setSubjects(jsonRespone));
  };

  const fetchSingleSubjects = () => {
    fetch("/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjectsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(subjectsforPaging.length / 10)));
  };

  const fetchModules = async () => {
    fetch(`/api/v1/subjects/${subjectId}/modules`)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  };
  const fetchSubjects = async () => {
    fetch(`/api/v1/subjects/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setSubjects(jsonRespones));
  };

  const removeSubject = (id) => {
    fetch("/api/v1/subjects/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchSubjects)
      .then(setOpen(false));
  };

  useEffect(() => {
    nameText.length > 0 ? fetchFilterSubjects() : fetchSubjects();
  }, [activePage, nameText]);

  const [open, setOpen] = useState(false);
  const [close, setClose] = useState(false);

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleSubjects();
    }
  }, [subjects]);

  return (
    <div>
      {create && (
        <div>
          <CreateSubjecPage />
        </div>
      )}
      {active && (
        <div className="edit">
          <EditSubjectObject id={active} />
        </div>
      )}

      {!active && !create && (
        <div id="subjects">
          <Input
            className="controls1"
            placeholder="Filtruoti pagal pavadinimą"
            value={nameText}
            onChange={(e) => setNameText(e.target.value)}
          />

          <Button
            icon
            labelPosition="left"
            primary
            className="controls"
            onClick={() => setCreate("new")}
          >
            <Icon name="database" />
            Kurti naują dalyką
          </Button>
          <Divider horizontal hidden></Divider>

          <Table selectable>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                <Table.HeaderCell>Moduliai</Table.HeaderCell>
                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              {subjects.map((subject) => (
                <Table.Row key={subject.id}>
                  <Table.Cell>{subject.name}</Table.Cell>
                  <Table.Cell>
                  
                
                  <td>
                    {subjects.modules?.map((module) => (
                      <li key={module.id} id={module.id}>
                        {module.name}
                      </li>
                    ))}
                  </td>
                
              
                  </Table.Cell>
                  <Table.Cell collapsing>
                    <Button
                      basic
                      primary
                      compact
                      icon="eye"
                      title="Peržiūrėti"
                      onClick={() => setActive(subject.id)}
                    ></Button>
                    <Button
                      basic
                      color="black"
                      compact
                      title="Ištrinti"
                      icon="trash alternate"
                      onClick={() => setOpen(subject.id)}
                    ></Button>

                    <Confirm
                      open={open}
                      header="Dėmesio!"
                      content="Ar tikrai norite ištrinti?"
                      cancelButton="Grįžti atgal"
                      confirmButton="Ištrinti"
                      onCancel={() => setOpen(false)}
                      onConfirm={() => removeSubject(open)}
                      size="small"
                    />
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>
          <Divider hidden></Divider>

          <ButtonGroup basic compact>
            <Button
              onClick={() =>
                setActivePage(activePage <= 0 ? activePage : activePage - 1)
              }
              icon
            >
              <Icon name="arrow left" />{" "}
            </Button>
            {[...Array(pagecount)].map((e, i) => {
                            return <Button key={i} active={activePage === i ? true : false} onClick={() => setActivePage(i) }>{i + 1}</Button>
                        })}
            <Button
              onClick={() =>
                setActivePage(
                  activePage >= pagecount - 1 ? activePage : activePage + 1
                )
              }
              icon
            >
              <Icon name="arrow right" />{" "}
            </Button>
          </ButtonGroup>
        </div>
      )}
    </div>
  );
}






