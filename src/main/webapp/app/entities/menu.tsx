import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/app-user">
        App User
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-lookup">
        App Lookup
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-topic-lookup">
        App Topic Lookup
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-topic-type">
        App Topic Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-subject-type">
        App Subject Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-subject">
        App Subject
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-subject-config">
        App Subject Config
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-user-group">
        App User Group
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
