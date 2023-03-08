import React from "react";
import { NavLink } from "react-router-dom";
import { Grid, Menu, Icon, Button, Divider } from "semantic-ui-react";

export function SchedulesMenu() {
  return (
    <div>
    
        <Button
          fluid
          id="details"
          icon
          labelPosition="right"
          className="controls3"
          as={NavLink}
          exact
          to="/create/groups"
        >
          <Icon name="calendar" />
          Kurti naują
        </Button>
        <Grid columns={1}>
        <Grid.Column>
          <Menu fluid vertical tabular id="main" className="ui centered grid">
            <Menu.Item
              id="icocolor"
              name="groups"
              icon="users"
              content="Grupės"
              as={NavLink}
              exact
              to="/view/groupsSchedules"
            />
            <Menu.Item
              id="icocolor"
              name="teachers"
              icon="user"
              content="Mokytojai"
              //   as={NavLink}
              //   exact
              //   to="/view/teachers"
            />
            <Menu.Item
              id="icocolor"
              name="rooms"
              icon="warehouse"
              content="Kabinetai"
              //   as={NavLink}
              //   exact
              //   to="/view/rooms"
            />
          </Menu>
        </Grid.Column>
      </Grid>
    </div>
  );
}
