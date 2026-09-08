document.addEventListener('DOMContentLoaded', () => {
  initTabNavigation();
  loadJohnSmithProfile();
  loadSkillCatalog();
  loadAssessments();
  loadCertifications();
  loadCompetencies();
  initRbacControls();
});

// Tab Navigation
function initTabNavigation() {
  const tabs = document.querySelectorAll('.nav-tab-btn');
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');

      const targetId = tab.getAttribute('data-target');
      document.querySelectorAll('.tab-screen').forEach(screen => {
        screen.classList.remove('active');
      });
      document.getElementById(targetId).classList.add('active');
    });
  });
}

// 1. Load John Smith Profile & Output Banner
async function loadJohnSmithProfile() {
  try {
    const res = await fetch('/api/v1/skill-profiles/john-smith');
    const data = await res.json();

    // Set Banner Output Text
    if (data.formattedOutputSummary) {
      document.getElementById('expectedOutputText').innerText = data.formattedOutputSummary;
    }

    // Populate Employee Profile Card
    const emp = data.employee;
    document.getElementById('empName').innerText = emp.name;
    document.getElementById('empTitle').innerText = emp.title + ' • ' + emp.department;
    document.getElementById('empEmail').innerText = emp.email;

    // Render Skills
    const skillsList = document.getElementById('empSkillsList');
    skillsList.innerHTML = '';
    data.skills.forEach(item => {
      const percent = item.proficiencyLevel * 10;
      skillsList.innerHTML += `
        <div style="margin-bottom: 1rem;">
          <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:0.3rem;">
            <span style="font-weight:600;">${item.skill.skillName}</span>
            <span style="color:var(--accent-cyan); font-family:var(--font-mono); font-weight:700;">${item.proficiencyLevel}/10</span>
          </div>
          <div class="progress-container">
            <div class="progress-bar" style="width: ${percent}%;"></div>
          </div>
        </div>
      `;
    });

    // Render Certifications in Profile
    const certsList = document.getElementById('empCertsList');
    certsList.innerHTML = '';
    data.certifications.forEach(cert => {
      const isExpired = cert.status === 'EXPIRED';
      certsList.innerHTML += `
        <div style="display:flex; justify-content:space-between; align-items:center; padding:0.75rem 0; border-bottom:1px solid rgba(255,255,255,0.05);">
          <div>
            <div style="font-weight:600; font-size:0.95rem;">${cert.certName}</div>
            <div style="font-size:0.78rem; color:var(--text-muted);">ID: ${cert.credentialId}</div>
          </div>
          <span class="badge ${isExpired ? 'badge-expired' : 'badge-valid'}">${cert.status}</span>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load John Smith profile:', err);
  }
}

// 2. Load Skill Catalog (2,847 skills metrics)
async function loadSkillCatalog() {
  try {
    const res = await fetch('/api/v1/skills');
    const skills = await res.json();
    const tableBody = document.getElementById('skillCatalogBody');
    tableBody.innerHTML = '';

    skills.forEach(s => {
      tableBody.innerHTML += `
        <tr>
          <td style="font-weight:700;">${s.skillName}</td>
          <td><span class="badge" style="background:rgba(255,255,255,0.06); color:var(--accent-cyan);">${s.category}</span></td>
          <td>${s.level}</td>
          <td style="color:var(--text-muted);">${s.description}</td>
          <td><span class="badge badge-verified">Active</span></td>
        </tr>
      `;
    });
  } catch (err) {
    console.error('Failed to load skills:', err);
  }
}

// 3. Load Assessment Scoring
async function loadAssessments() {
  try {
    const res = await fetch('/api/v1/assessments');
    const assessments = await res.json();
    const container = document.getElementById('assessmentContainer');
    container.innerHTML = '';

    assessments.forEach(a => {
      container.innerHTML += `
        <div class="glass-card">
          <div style="display:flex; justify-content:space-between; align-items:flex-start;">
            <div>
              <div style="font-weight:700; font-size:1.1rem; margin-bottom:0.25rem;">${a.title}</div>
              <div style="font-size:0.85rem; color:var(--text-muted);">Assessed Employee: <strong>${a.employee.name}</strong> (${a.employee.title})</div>
            </div>
            <div style="font-size:1.8rem; font-weight:800; color:var(--accent-emerald); font-family:var(--font-mono);">${a.score}%</div>
          </div>
          <div style="margin-top:1rem; display:flex; justify-content:space-between; align-items:center; font-size:0.82rem; color:var(--text-dim);">
            <span>Date: ${a.assessmentDate}</span>
            <span class="badge badge-verified">Verified Assessment</span>
          </div>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load assessments:', err);
  }
}

// 4. Load Certification Tracking
async function loadCertifications() {
  try {
    const res = await fetch('/api/v1/certifications');
    const certs = await res.json();
    const tableBody = document.getElementById('certTableBody');
    tableBody.innerHTML = '';

    certs.forEach(c => {
      const isExpired = c.status === 'EXPIRED';
      tableBody.innerHTML += `
        <tr>
          <td style="font-weight:700;">${c.employee.name}</td>
          <td>${c.certName}</td>
          <td style="font-family:var(--font-mono);">${c.credentialId}</td>
          <td>${c.issueDate}</td>
          <td>${c.expiryDate}</td>
          <td><span class="badge ${isExpired ? 'badge-expired' : 'badge-valid'}">${c.status}</span></td>
        </tr>
      `;
    });
  } catch (err) {
    console.error('Failed to load certifications:', err);
  }
}

// 5. Load Competency Mapping
async function loadCompetencies() {
  try {
    const res = await fetch('/api/v1/competencies');
    const competencies = await res.json();
    const container = document.getElementById('competencyContainer');
    container.innerHTML = '';

    competencies.forEach(comp => {
      container.innerHTML += `
        <div class="glass-card">
          <div class="card-title">
            <span>Target Role: ${comp.roleTitle}</span>
            <span style="font-size:0.85rem; color:var(--accent-cyan); font-weight:600;">${comp.department} Dept</span>
          </div>
          <p style="font-size:0.9rem; color:var(--text-muted); margin-bottom:1rem;">${comp.targetProficiencySummary}</p>
          <div style="display:flex; justify-content:space-between; align-items:center; font-size:0.85rem;">
            <span>Required Pass Benchmark: <strong>${comp.minRequiredScore}%</strong></span>
            <button class="btn-primary" style="padding:0.4rem 0.85rem; font-size:0.8rem;">Evaluate Gap</button>
          </div>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load competencies:', err);
  }
}

// 6. RBAC Controls
function initRbacControls() {
  let currentRole = 'HR';
  const roleBtns = document.querySelectorAll('.role-btn');
  roleBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      roleBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      currentRole = btn.getAttribute('data-role');
      document.getElementById('activeRoleDisplay').innerText = currentRole;
    });
  });

  const testActionBtn = document.getElementById('testRbacBtn');
  testActionBtn.addEventListener('click', async () => {
    try {
      const res = await fetch(`/api/v1/rbac/verify?role=${currentRole}&action=EDIT_SKILL_MATRIX`, { method: 'POST' });
      const result = await res.json();
      const outputDiv = document.getElementById('rbacOutput');
      outputDiv.innerHTML = `
        <div class="glass-card" style="border-color:${result.accessGranted ? 'var(--accent-emerald)' : 'var(--accent-rose)'};">
          <div style="font-weight:700; color:${result.accessGranted ? 'var(--accent-emerald)' : 'var(--accent-rose)'};">
            ${result.message}
          </div>
          <div style="font-size:0.8rem; margin-top:0.5rem; color:var(--text-muted);">
            Action: <code>${result.actionRequested}</code> | Active Role: <code>${result.role}</code>
          </div>
        </div>
      `;
    } catch (err) {
      console.error('RBAC test failed:', err);
    }
  });
}
