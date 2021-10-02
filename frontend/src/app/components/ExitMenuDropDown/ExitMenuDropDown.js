import React from 'react';
import { Button, Dropdown } from 'antd';
import { CaretUpFilled } from '@ant-design/icons';
import ExitMenuTooltip from '../ExitMenuTooltip/ExitMenuTooltip';

export default function ExitMenuDropDown() {
  return (
      <Dropdown
        overlay={<ExitMenuTooltip />}
        placement="bottomLeft"
        arrow
        trigger="click"
        className="exit_menu_dropdown"
      >
          <Button
            id="exit_menu_button"
            icon={(
                <CaretUpFilled
                  className="exit_menu_ellipsis_icon"
                />
)}
          />
      </Dropdown>
  );
}
