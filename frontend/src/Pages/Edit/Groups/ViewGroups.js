import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input,
  Pagination,
  Segment,
  Table,
} from "semantic-ui-react";
import { YEAR_OPTIONS } from "../../../Components/const";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewGroups() {
  // const yearOptions = [
  //     { key: 23, value: 2023, text: '2023' },
  //     { key: 24, value: 2024, text: '2024' },
  //     { key: 25, value: 2025, text: '2025' },
  //     { key: 26, value: 2026, text: '2026' },
  //     { key: 27, value: 2027, text: '2027' },
  //     { key: 28, value: 2028, text: '2028' },
  // ]

  const [active, setActive] = useState();

  const [groups, setGroups] = useState([]);

  const [groupsforPaging, setGroupsForPaging] = useState([]);

  const [nameText, setNameText] = useState("");

  const [yearText, setYearText] = useState("");

  const [programText, setProgramText] = useState("");

  const [activePage, setActivePage] = useState(0);

  const [pagecount, setPageCount] = useState();

  const fetchProgramGroups = async () => {
    fetch("/api/v1/groups/program-filter/" + programText)
      .then((response) => response.json())
      .then((jsonResponse) => setGroups(jsonResponse));
  };
  const fetchYearGroups = async () => {
    fetch("/api/v1/groups/year-filter/" + yearText)
      .then((response) => response.json())
      .then((jsonResponse) => setGroups(jsonResponse));
  };

  const fetchFilterGroups = async () => {
    fetch("/api/v1/groups/name-filter/" + nameText)
      .then((response) => response.json())
      .then((jsonResponse) => setGroups(jsonResponse));
  };

  const fetchGroups = async () => {
    fetch("/api/v1/groups/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setGroups(jsonResponse));
  };

  const fetchSingleGroups = async () => {
    fetch("/api/v1/groups/")
      .then((response) => response.json())
      .then((jsonResponse) => setGroupsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(groupsforPaging.length / 10)));
    // .then(console.log('pages:' + pagecount));
  };

  const removeGroup = (id) => {
    fetch("/api/v1/groups/delete/" + id, {
      method: "PATCH",
    })
      .then(fetchGroups)
      .then(setOpen(false));
  };

  useEffect(() => {
    if (
      (nameText.length === 0 &&
        yearText.length === 0 &&
        programText.length === 0)
    ) {
      fetchGroups();
      // setYearText('2023')
    } else if (nameText.length > 0 ) {
      setProgramText("");
      setYearText("");
      fetchFilterGroups();
    } else if (yearText > 0 ) {
      setNameText("");
      setProgramText("");
      fetchYearGroups();
    } else if (programText.length > 0) {
      setNameText("");
      setYearText("");
      fetchProgramGroups();
    }
  }, [nameText, yearText, programText, activePage]);

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleGroups();
    }
  }, [groups]);

  const [open, setOpen] = useState(false);


  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>
        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" color="teal">
            <div>
              <Input
                title="Filtruoti pagal pavadinimą"
                className="controls1"
                placeholder="Filtruoti pagal pavadinimą"
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
              />

              <select
                id="selectYear"
                value={yearText}
                onChange={(e) => setYearText(e.target.value)}
                >  
                <option value="">Filtruoti pagal metus</option>              
                {Object.entries(YEAR_OPTIONS).map(([key, value]) => (
                  <option value={key}>{value}</option>
                ))}
              </select>

              <Input
                className="controls2"
                title="Filtruoti pagal programą"
                placeholder="Filtruoti pagal programą"
                value={programText}
                onChange={(e) => setProgramText(e.target.value)}
              />

              <Button
                id="details"
                title="Kurti naują grupę"
                icon
                labelPosition="left"
                className="controls"
                as={NavLink}
                exact
                to="/create/groups"
              >
                <Icon name="database" />
                Kurti naują grupę
              </Button>

              <Divider horizontal hidden></Divider>

              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Grupės pavadinimas "Teams"
                    </Table.HeaderCell>
                    <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                    <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                    <Table.HeaderCell>Programa</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {groups.map((group) => (
                    <Table.Row key={group.id}>
                      <Table.Cell>{group.name}</Table.Cell>
                      <Table.Cell>{group.schoolYear}</Table.Cell>
                      <Table.Cell>{group.studentAmount}</Table.Cell>
                      <Table.Cell>{group.programName}</Table.Cell>
                      <Table.Cell collapsing>
                        <Button
                          id="icocolor"
                          href={"#/view/groups/edit/" + group.id}
                          basic
                          compact
                          icon="eye"
                          title="Peržiūrėti"
                          onClick={() => setActive(group.id)}
                        ></Button>
                        <Button
                          id="icocolor"
                          basic
                          compact
                          title="Suarchyvuoti"
                          icon="archive"
                          onClick={() => setOpen(group.id)}
                        ></Button>

                        <Confirm
                          open={open}
                          header="Dėmesio!"
                          content="Ar tikrai norite perkelti į archyvą?"
                          cancelButton="Grįžti atgal"
                          confirmButton="Taip"
                          onCancel={() => setOpen(false)}
                          onConfirm={() => removeGroup(open)}
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
                    setActivePage(activePage <= 0 ? activePage : activePage - 1)
                  }
                  icon
                >
                  <Icon name="arrow left" />{" "}
                </Button>
                {/* {[...Array(pagecount)].map((e, i) => {
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
                })} */}
                <Button
                  title="Pirmyn"
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
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
