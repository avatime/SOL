import { FC, useEffect, useState } from "react";
import {
  Box,
  Dialog,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  Slide,
  Stack,
  Tab,
  Tabs,
  Typography,
} from "@mui/material";
import { TransitionProps } from "@mui/material/transitions";
import React from "react";
import BankInfoRes from "../apis/response/BankInfoRes";
import ApiClient from "../apis/ApiClient";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface Props {
  open: boolean;
  onClose: () => void;
  onClickBankItem: (bankInfoRes: BankInfoRes) => void;
}

export default function SelectBankBottomDialog({ open, onClose, onClickBankItem }: Props) {
  const [bankInfos, setBankInfos] = useState<BankInfoRes[]>([]);
  const [financeBankInfos, setFinanceBankInfos] = useState<BankInfoRes[]>([]);

  useEffect(() => {
    ApiClient.getInstance()
      .getBankInfos()
      .then((data) => setBankInfos(data));

    ApiClient.getInstance()
      .getFinanceBankInfos()
      .then((data) => setFinanceBankInfos(data));
  }, []);

  const [tabIndex, setTabIndex] = useState(0);
  useEffect(() => {
    setTabIndex(0);
  }, [open]);
  return (
    <Dialog
      PaperProps={{
        sx: {
          position: "absolute",
          left: 0,
          bottom: 0,
          width: "100vw",
          margin: 0,
          height: "70vh",
          borderTopLeftRadius: "15px",
          borderTopRightRadius: "15px",
          overflow: "hidden",
        },
      }}
      open={open}
      TransitionComponent={Transition}
      keepMounted
      onClose={onClose}
      aria-describedby="alert-dialog-slide-description"
    >
      <Tabs
        value={tabIndex}
        onChange={(_, idx) => setTabIndex(idx)}
        textColor="secondary"
        indicatorColor="secondary"
      >
        <Tab label="은행" />
        <Tab label="증권" />
      </Tabs>
      <TabPanel value={tabIndex} index={0}>
        <DrawTwoColumn items={bankInfos} onClickItem={onClickBankItem} />
      </TabPanel>
      <TabPanel value={tabIndex} index={1}>
        <DrawTwoColumn items={financeBankInfos} onClickItem={onClickBankItem} />
      </TabPanel>
    </Dialog>
  );
}

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <Box
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
      style={{
        overflow: "auto",
      }}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </Box>
  );
}

interface DrawTwoColumnProps {
  items: BankInfoRes[];
  onClickItem: (bankInfoRes: BankInfoRes) => void;
}

function DrawTwoColumn({ items, onClickItem }: DrawTwoColumnProps) {
  return (
    <Grid container direction="row">
      {items.map((it, idx) => (
        <Grid key={idx} item xs={6} onClick={() => onClickItem(it)}>
          <Stack direction="row" alignItems="center" my={1}>
            <img src={it.cp_logo} style={{ width: "24px", height: "24px", borderRadius: "20px" }} />
            <Box m={1} />
            <span style={{ fontSize: "12px" }}>{it.cp_name}</span>
          </Stack>
        </Grid>
      ))}
    </Grid>
  );
}
