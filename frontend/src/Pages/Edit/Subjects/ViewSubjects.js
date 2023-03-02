import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input, List, Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";
import { CreateSubjecPage } from "./CreateSubjectPage";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewSubjects() {
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [nameText, setNameText] = useState("");
  const [subjects, setSubjects] = useState([]);

  const [subjectsforPaging, setSubjectsForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();
  const [moduleText, setModuleText] = useState("");

  const fetchSubjectsByModules = async () => {
    fetch(`/scheduler/api/v1/subjects/page/module-filter/${moduleText}`)
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  };

  const fetchFilterSubjects = async () => {
    fetch(`/scheduler/api/v1/subjects/page/name-filter/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setSubjects(jsonRespone));
  };

  const fetchSingleSubjects = () => {
    fetch("/scheduler/api/v1/subjects")
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
    fetch(`/scheduler/api/v1/subjects/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setSubjects(jsonRespones));
  };

  const removeSubject = (id) => {
    fetch("/scheduler/api/v1/subjects/delete/" + id, {
      method: "PATCH",
    }).then(fetchSubjects)
      .then(setOpen(false));
  };

  // useEffect(() => {
  //   nameText.length > 0 ? fetchFilterSubjects() : fetchSubjects();
  // }, [activePage, nameText]);

  // useEffect(() => {
  //   moduleText.length > 0 ? fetchSubjectsByModules() : fetchSubjects();
  // }, [activePage, moduleText]);


  useEffect(() => {
    nameText.length > 0 ? fetchFilterSubjects() : (moduleText.length > 0 ? fetchSubjectsByModules() : fetchSubjects())
  }, [activePage, nameText, moduleText]);


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
          <Segment id="segment" color="teal">
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
                  className="controls"
                  id='details'
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
                    {subjects.map((subject, index) => (
                      <Table.Row key={index}>
                        <Table.Cell>{subject.name}</Table.Cell>
                        <Table.Cell>
                          <List bulleted>
                            {console.log(subject.subjectModules)}
                            {subject.subjectModules.map((module, index) => (
                              <List.Content key={index}>
                                <List.Item>{module.name}</List.Item>
                              </List.Content>
                            ))}
                          </List>
                        </Table.Cell>
                        <Table.Cell collapsing>
                          <Button
                            id="icocolor"
                            basic
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            href={"#/view/subjects/edit/" + subject.id}
                            onClick={() => setActive(subject.id)}
                          ></Button>
                          <Button
                            id="icocolor"
                            basic
                            compact
                            title="Suarchyvuoti"
                            icon="archive"
                            onClick={() => setOpen(subject.id)}
                          ></Button>

                          <Confirm
                            open={open}
                            header="Dėmesio!"
                            content="Ar tikrai norite perkelti į archyvą?"
                            cancelButton="Grįžti atgal"
                            confirmButton="Taip"
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
