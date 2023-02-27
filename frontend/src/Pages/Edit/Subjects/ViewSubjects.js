import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input,
  Segment,
  Table,
  List,
} from "semantic-ui-react";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';
import { CreateSubjecPage } from "./CreateSubjectPage";
import { NavLink } from "react-router-dom";

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
  const [modulesInSubjects, setModulesInSubjects] = useState([]);
  const [moduleText, setModuleText] = useState();

  const fetchSubjectsBYModules = async () => {
    fetch("http://localhost:8081/scheduler/api/v1/subjects/module-filter/" + moduleText)
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  };

  const fetchFilterSubjects = async () => {
    fetch(`http://localhost:8081/scheduler/api/v1/subjects/page/name-filter/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setSubjects(jsonRespone));
  };

  const fetchSingleSubjects = () => {
    fetch("http://localhost:8081/scheduler/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjectsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(subjectsforPaging.length / 10)));
  };

  // const fetchModules = async () => {
  //   fetch(`/api/v1/subjects/${subjectId}/modules`)
  //     .then((response) => response.json())
  //     .then((jsonRespones) => setModules(jsonRespones));
  // };
  const fetchSubjects = async () => {
    fetch(`http://192.168.0.129:8081/scheduler/api/v1/subjects/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setSubjects(jsonRespones));
  };

  const removeSubject = (id) => {
    fetch("http://192.168.0.129:8081/scheduler/api/v1/subjects/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchSubjects);
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
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="groups" />
        </Grid.Column>

        <Grid.Column stretched textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" raised color="teal">
            {create && (
              <div>
                <CreateSubjecPage />
              </div>
            )}

            {!active && !create && (
              <div id="subjects">
                <Input
                  className="controls1"
                  placeholder="Filtruoti pagal dalyką"
                  value={nameText}
                  onChange={(e) => setNameText(e.target.value)}
                />
                <Input
                  className="controls1"
                  value={moduleText}
                  onChange={(e) => setModuleText(e.target.value)}
                  placeholder="Filtruoti pagal modulį"
                />

                <Button
                  icon
                  labelPosition="left"
                  primary
                  className="controls"
                  as={NavLink}
                  exact
                  to="/create/subjects"
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
                    <List bulleted>
                      {console.log(subject.subjectModules)}
                      {subject.subjectModules.map((module) => (
                        <List.Content key={module.id}>
                          <List.Item>{module.name}</List.Item>
                        </List.Content>
                      ))}
                    </List>
                  </Table.Cell>
                  <Table.Cell collapsing>
                          <Button
                            basic
                            primary
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            href={"#/view/subjects/edit/" + subject.id}
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

                <ButtonGroup compact basic>
                  <Button
                    title="Atgal"
                    onClick={() =>
                      setActivePage(
                        activePage <= 0 ? activePage : activePage - 1
                      )
                    }
                    icon
                  >
                    <Icon name="arrow left" />{" "}
                  </Button>
                  {[...Array(pagecount)].map((e, i) => {
                    return (
                      <Button
                        title={i + 1}
                        key={i}
                        active={activePage === i ? true : false}
                        onClick={() => setActivePage(i)}
                      >
                        {i + 1}
                      </Button>
                    );
                  })}
                  <Button
                    title="Pirmyn"
                    onClick={() =>
                      setActivePage(
                        activePage >= pagecount - 1
                          ? activePage
                          : activePage + 1
                      )
                    }
                    icon
                  >
                    <Icon name="arrow right" />{" "}
                  </Button>
                </ButtonGroup>
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
