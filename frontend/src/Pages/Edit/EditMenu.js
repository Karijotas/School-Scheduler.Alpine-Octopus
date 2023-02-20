import React from 'react'
import { NavLink, } from 'react-router-dom';
import { Grid, Menu, } from 'semantic-ui-react'

export function EditMenu() {


  return (
    <div >
      <Grid columns={1} >
        <Grid.Column >
          <Menu color='blue' fluid vertical secondary className='ui centered grid'>
            <Menu.Item
              name='subjects'
              icon='book'
              content='Dalykai'
              as={NavLink}
              exact
              to='/view/subjects'
            />
            <Menu.Item
              name='teachers'
              icon='user'
              content='Mokytojai'
              as={NavLink}
              exact
              to='/view/teachers'
            />
            <Menu.Item
              name='groups'
              icon='users'
              content='Grupės'
              as={NavLink}
              exact
              to='/view/groups'
            />
            <Menu.Item
              name='shifts'
              content='Pamainos'
              icon='sync'
              as={NavLink}
              exact
              to='/view/shifts'
            />
            <Menu.Item
              name='rooms'
              icon='warehouse'
              content='Klasės'
              as={NavLink}
              exact
              to='/view/rooms'
            />
            <Menu.Item
              name='program'
              icon='file alternate'
              content='Programos'
              as={NavLink}
              exact
              to='/view/programs'
            />
            <Menu.Item
              name='modules'
              content='Moduliai'
              icon='unordered list'
              as={NavLink}
              exact
              to='/view/modules'
            />
          </Menu>
        </Grid.Column>
      </Grid>
    </div>
  )
}

